// https://docs.aws.amazon.com/AWSAndroidSDK/latest/javadoc/

package com.randoworks.pigskinscores;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.io.File;
import java.util.concurrent.CountDownLatch;

// Application utility methods for use throughout application
// Namely holds methods for AWS service APIs
public class Util {

    private static AmazonS3Client sS3Client;
    private static AWSCredentialsProvider sMobileClient;
    private static TransferUtility sTransferUtility;

    /**
     * Gets an instance of AWSMobileClient which is
     * constructed using the given Context.
     *
     * @param context Android context
     * @return AWSMobileClient which is a credentials provider
     */
    private static AWSCredentialsProvider getCredProvider(Context context) {
        if (sMobileClient == null) {
            final CountDownLatch LATCH = new CountDownLatch(1);
            AWSMobileClient.getInstance().initialize(context, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails result) {
                    LATCH.countDown();
                }

                @Override
                public void onError(Exception e) {
                    // TODO: Implement switch to error screen
                    LATCH.countDown();
                }
            });
            try {
                LATCH.await();
                sMobileClient = AWSMobileClient.getInstance();
            } catch (InterruptedException e) {
                // TODO: Implement switch to error screen
                e.printStackTrace();
            }
        }
        return sMobileClient;
    }

    /**
     * Gets an instance of a S3 client which is constructed using the given
     * Context.
     *
     * @param context Android context
     * @return A default S3 client.
     */
    private static AmazonS3Client getS3Client(Context context) {
        if (sS3Client == null) {
            try {
                String regionString = new AWSConfiguration(context)
                        .optJsonObject("S3TransferUtility")
                        .getString("Region");
                Region region = Region.getRegion(regionString);
                sS3Client = new AmazonS3Client(getCredProvider(context), region);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sS3Client;
    }


    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @param context Android context
     * @return a TransferUtility instance
     */
    public static TransferUtility getTransferUtility(Context context) {
        if (sTransferUtility == null) {
            sTransferUtility = TransferUtility.builder()
                    .context(context)
                    .s3Client(getS3Client(context))
                    .awsConfiguration(new AWSConfiguration(context))
                    .build();
        }
        return sTransferUtility;
    }

    // Handles opening file/file pointer creation
    public static File openFile(String fileKey, Context context) {
        return new File(context.getFilesDir(), fileKey);
    }

    // Determines whether device has internet connectivity
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    // Display snackbar detailing error
    // Pretty much any error in the app is assumed to be the result of a bad connection
    public static void showNoConnectionSnack(View view) {
        Snackbar.make(view, R.string.no_connection_snackbar_string,
                BaseTransientBottomBar.LENGTH_LONG).show();
    }


}
