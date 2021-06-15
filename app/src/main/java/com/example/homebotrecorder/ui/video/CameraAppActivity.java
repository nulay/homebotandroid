package com.example.homebotrecorder.ui.video;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class CameraAppActivity extends AppCompatActivity {
    private static final int VIDEO_CAPTURE = 101;
    private Uri fileUri;

    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

    public void startRecordingq(View view)
    {

        File mediaFile = new
                File("d://myvideo.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = Uri.fromFile(mediaFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    public void stopRecording(View view)
    {
        startActivityForResult(intent, VIDEO_CAPTURE);
    }
}
