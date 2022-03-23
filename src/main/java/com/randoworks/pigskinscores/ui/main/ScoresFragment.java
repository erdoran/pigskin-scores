package com.randoworks.pigskinscores.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.randoworks.pigskinscores.R;
import com.randoworks.pigskinscores.Util;
import com.randoworks.pigskinscores.databinding.FragmentScoresBinding;

import static com.randoworks.pigskinscores.DownloadJsonFile.startDownload;
import static com.randoworks.pigskinscores.Util.showNoConnectionSnack;

// Main application Fragment
// Displays Scores
public class ScoresFragment extends Fragment {

    private FragmentScoresBinding mViewBinding;

    public static ScoresFragment newInstance() {
        return new ScoresFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentScoresBinding.inflate(inflater, container,
                false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewBinding.scoresSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshUpdate();
                    }
                });
        mViewBinding.scoresSwipeRefresh.setRefreshing(true);
        startDownload(getActivity(), mViewBinding.scoresLlGames,
                mViewBinding.scoresSwipeRefresh);
    }

    private void swipeRefreshUpdate() {
        if (Util.isConnected(getActivity())) {
            mViewBinding.scoresLlGames.removeAllViews();
            startDownload(getActivity(), mViewBinding.scoresLlGames,
                    mViewBinding.scoresSwipeRefresh);
        } else {
            mViewBinding.scoresSwipeRefresh.setRefreshing(false);
            showNoConnectionSnack(getActivity().findViewById(R.id.main_coordinator));
        }
    }
}