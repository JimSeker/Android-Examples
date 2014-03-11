package edu.cs4730.VideoPlay;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class videoPlay extends Activity {
  VideoView vv;
  
  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Get the ViewView
        vv = (VideoView) this.findViewById( R.id.videoView);
        //add media controls to it.
        vv.setMediaController(new MediaController(this));
        //Setup where the file to play is
        //Uri videoUri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/the-empire.3gp");
        Uri VideoUri = Uri.parse("http://www.cs.uwyo.edu/~seker/courses/4730/example/the-empire.3gp");
        vv.setVideoURI(VideoUri);
        //play the video
        vv.start();

    }
}