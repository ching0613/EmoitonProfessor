package com.example.wen_cheng.emoitonprofessor;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QuesActivity extends AppCompatActivity {

    public AudioManager audioManager;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        listView = (ListView)findViewById(R.id.list);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){

            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){

            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

        }else if(keyCode == KeyEvent.KEYCODE_BACK){
        }
        return false;
    }
}
