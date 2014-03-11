package edu.cs4730.AudioPlay;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class audioPlay extends Activity {
  static final String AUDIO_PATH = "http://www.cs.uwyo.edu/~seker/courses/4730/example/MEEPMEEP.WAV";
  //"http://java.sun.com/products/java-media/mma/media/test-wav.wav";
  //"/sdcard/file.mp3"
  Button btnStart, btnPause, btnRestart;
  MediaPlayer mediaPlayer;
  int playbackPosition = 0;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    btnStart = (Button)findViewById(R.id.btnPlay);
    btnStart.setOnClickListener( new OnClickListener() {
      @Override public void onClick(View view) {
        playAudio(AUDIO_PATH);
      }
    }
    );
    btnPause = (Button)findViewById(R.id.btnPause);
    btnPause.setOnClickListener( new OnClickListener() {
      @Override public void onClick(View view) {
        pauseAudio();
      }
    }
    );
    btnRestart = (Button)findViewById(R.id.btnRestart);
    btnRestart.setOnClickListener( new OnClickListener() {
      @Override public void onClick(View view) {
        restartAudio();
      }
    }
    );
  }
  /*
   * (non-Javadoc)
   * @see android.app.Activity#onPause()
   * Kill the media player if this gets pushed into the background.
   */
  @Override protected void onPause() {
     KillMediaPlayer();
  }
  @Override protected void onDestroy() {
    KillMediaPlayer();
    super.onDestroy();
  }
  void pauseAudio() {
     if (mediaPlayer != null && mediaPlayer.isPlaying()) {
       playbackPosition = mediaPlayer.getCurrentPosition();
       mediaPlayer.pause();
     }
  }
  void playAudio(String url) {
    if (mediaPlayer == null) { //first time
      mediaPlayer = new MediaPlayer();
      try {
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
      } catch (Exception e) {
        Toast.makeText(getBaseContext(), "Exception, not playing", 
            Toast.LENGTH_SHORT).show();
        System.out.println("Player exception is "+ e.getMessage());
        return;
      } 
    } else if (mediaPlayer.isPlaying()) {  //duh don't start it again.
      Toast.makeText(getBaseContext(), "I'm playing already", Toast.LENGTH_SHORT).show();
      return;    
    } else {  //play it at least one, reset and play again.
      mediaPlayer.seekTo(0);
    }
    mediaPlayer.start();

  }
  void restartAudio() {
    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
      mediaPlayer.seekTo(playbackPosition);
      mediaPlayer.start();
    }
      
  }
  void KillMediaPlayer() {
    if (mediaPlayer != null)
      mediaPlayer.release();
  }
  
}