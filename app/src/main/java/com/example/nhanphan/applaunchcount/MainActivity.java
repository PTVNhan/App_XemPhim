package com.example.nhanphan.applaunchcount;
import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;
public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController; //android.widget
    LinearLayout mainLayout;
    TextView txtAppLanchCount;
    ToggleButton toggleButton;
    int color, count = 0;
    private String PREFNAME = "AppLaunchCount";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.myVideoView);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        txtAppLanchCount = (TextView) findViewById(R.id.textViewCount);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.tomjerry;
        videoView.setVideoURI(Uri.parse(videoPath));
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    color = R.color.colorDark;
                }else{
                    color = R.color.colorWhite;
                }
                mainLayout.setBackgroundResource(color);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        int countDefault = 0;

        if (preferences != null && preferences.contains("appLaunchCount")){
            count = preferences.getInt("appLaunchCount", countDefault);
        }
        count++;
        txtAppLanchCount.setText(getString(R.string.app_launch_count) + ": " + count);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("appLaunchCount",count);
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }
}
