package com.example.week5loginscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity{
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView useremailTxt, youGotTxt;
    private Button btn_logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Initialise
        auth = FirebaseAuth.getInstance();
        useremailTxt = findViewById(R.id.txt_user_signed_in);
        btn_logout = findViewById(R.id.btn_logout);
        youGotTxt = findViewById(R.id.txt_rickroll);
        // get the user
        user = auth.getCurrentUser();
        // If no user
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            // Get the users email
            useremailTxt.setText(useremailTxt.getText() + user.getEmail());

        }

        // Surprise Video
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.week5login_rr_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Control buttons like play, stop, forward
        MediaController mediaController = new MediaController(this) {
            @Override
            public void show(int timeout) {
                super.show(timeout);
                // Change the text when video playback starts
                youGotTxt.setText("You just got....");
            }
        };
        // Pass the media controller you created above
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();
                // Close this activity and open login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
