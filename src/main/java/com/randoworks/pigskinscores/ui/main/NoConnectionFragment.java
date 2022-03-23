package com.randoworks.pigskinscores.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.randoworks.pigskinscores.R;
import com.randoworks.pigskinscores.Util;
import com.randoworks.pigskinscores.databinding.FragmentNoConnectionBinding;

import org.jetbrains.annotations.NotNull;

import static com.randoworks.pigskinscores.Util.showNoConnectionSnack;

// Fragment displays when app is launched without Internet connection

public class NoConnectionFragment extends Fragment {

    private FragmentNoConnectionBinding mViewBinding;

    public static NoConnectionFragment newInstance() {
        return new NoConnectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mViewBinding = FragmentNoConnectionBinding.inflate(inflater, container,
                false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewBinding.swipeRefreshNoConnection.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (Util.isConnected(getActivity())) {
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_fragment_container,
                                            new ScoresFragment(), null)
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            mViewBinding.swipeRefreshNoConnection.setRefreshing(false);
                            showNoConnectionSnack
                                    (getActivity().findViewById(R.id.main_coordinator));
                        }
                    }
                });

    }
}