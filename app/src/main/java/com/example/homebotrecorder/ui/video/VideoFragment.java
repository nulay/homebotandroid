package com.example.homebotrecorder.ui.video;


import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.homebotrecorder.R;

public class VideoFragment extends Fragment {

    private VideoViewModel mViewModel;
    private CameraAppActivity cameraAppActivity = new CameraAppActivity();
    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentActivity activ = getActivity();
//        activ.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.video_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);
        // TODO: Use the ViewModel
    }


    public void startStopVideo(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);
        // TODO: Use the ViewModel
    }

    public void startRecording(View view)
    {
        cameraAppActivity.startRecordingq(view);
    }

    public void display(int number) {

    }
}