package com.randoworks.pigskinscores;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.randoworks.pigskinscores.databinding.ActivityMainBinding;
import com.randoworks.pigskinscores.ui.main.NoConnectionFragment;
import com.randoworks.pigskinscores.ui.main.ScoresFragment;

import static com.randoworks.pigskinscores.Util.isConnected;
import static com.randoworks.pigskinscores.Util.showNoConnectionSnack;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set app theme to remove splash screen
        setTheme(R.style.Theme_PigskinScores);

        super.onCreate(savedInstanceState);
        mViewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);

        // Set action bar
        setSupportActionBar(mViewBinding.mainToolbar);

        mViewBinding.mainToolbarBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBtnClick();
            }
        });

        // Check for connection and launch appropriate fragment
        if (isConnected(this)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.main_fragment_container, new ScoresFragment(), null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.main_fragment_container, new NoConnectionFragment(), null)
                    .commit();
        }

    }

    // Button update method
    private void updateBtnClick() {
        if (isConnected(this)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.main_fragment_container, new ScoresFragment(), null)
                    .commit();
        } else {
            showNoConnectionSnack(mViewBinding.mainCoordinator);
        }

    }
}