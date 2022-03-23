package com.randoworks.pigskinscores;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.util.ArrayList;

import static com.randoworks.pigskinscores.CreateLayout.createInProgressLayout;
import static com.randoworks.pigskinscores.CreateLayout.createPostgameLayout;
import static com.randoworks.pigskinscores.CreateLayout.createPregameLayout;
import static com.randoworks.pigskinscores.JsonParser.parseJson;
import static com.randoworks.pigskinscores.Util.getTransferUtility;
import static com.randoworks.pigskinscores.Util.openFile;
import static com.randoworks.pigskinscores.Util.showNoConnectionSnack;

// Implements the S3 transfeutility to download the data JSON file
public class DownloadJsonFile {

    private static final String sFILE_KEY = "game_data.json";
    private static TransferUtility sTransferUtility;

    public static void startDownload(Context context, LinearLayout ll, SwipeRefreshLayout srl) {
        sTransferUtility = getTransferUtility(context);
        sTransferUtility.download(sFILE_KEY, openFile(sFILE_KEY, context), new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    handleCompleted(context, ll);
                    srl.setRefreshing(false);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                // Not really needed in this application
            }

            @Override
            public void onError(int id, Exception ex) {
                handleDownloadFailure(context, ll);
            }
        });
    }

    // Upon download completion, parse JSON file, create layouts, and add them to fragment
    private static void handleCompleted(Context context, LinearLayout ll) {
        ArrayList<Game> games = parseJson(context, ll);
        int length = games.size();
        for (Game game : games) {
            if (game.getGameStatus().equals("1")) {
                ll.addView(createInProgressLayout(game, length, context));
                length--;
            }
        }
        for (Game game : games) {
            if (game.getGameStatus().equals("0")) {
                ll.addView(createPregameLayout(game, length, context));
                length--;
            }
        }

        for (Game game : games) {
            if (game.getGameStatus().equals("2")) {
                ll.addView(createPostgameLayout(game, length, context));
                length--;
            }
        }
    }

    // If download fails, attempt to use cached file
    private static void handleDownloadFailure(Context context, LinearLayout ll) {
        try {
            handleCompleted(context, ll);
            showNoConnectionSnack(ll);
        } catch (Exception e) {
            showNoConnectionSnack(ll);
        }
    }
}
