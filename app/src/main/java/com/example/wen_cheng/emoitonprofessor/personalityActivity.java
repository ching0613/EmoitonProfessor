package com.example.wen_cheng.emoitonprofessor;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class personalityActivity extends AppCompatActivity {

    public AudioManager audioManager;
    public TextView textView;
    public int t1,t2,t3,t4;
    private int a=0 , b=0 , c=0 , d=0 , count=1;
    protected Button opa,opb,opc,opd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        Declare();
    }

    private void Declare() {
        textView = (TextView)findViewById(R.id.textView2);
        opa = (Button)findViewById(R.id.option_A);
        opb = (Button)findViewById(R.id.option_B);
        opc = (Button)findViewById(R.id.option_C);
        opd = (Button)findViewById(R.id.option_D);
    }

    public void bclick(View view){

        switch (view.getId()){
            case R.id.option_A:{
                analysis();
                break;
            }
            case R.id.option_B:{
                analysis();
                break;
            }
            case R.id.option_C:{
                analysis();
                break;
            }
            case R.id.option_D:{
                analysis();
                break;
            }
        }
    }

    private void analysis() {
        if(count <= 10) {
            ran();
            a = a + t1;
            b = b + t2;
            c = c + t3;
            d = d + t4;
            textView.setText(Integer.toString(a) + Integer.toString(b) +
                    Integer.toString(c) + Integer.toString(d));
            ++count;
        }else if (count == 11){
            opa.setClickable(false);
            opa.setVisibility(View.INVISIBLE);
            opb.setClickable(false);
            opb.setVisibility(View.INVISIBLE);
            opc.setClickable(false);
            opc.setVisibility(View.INVISIBLE);
            opd.setText("查看分析結果");
            ++count;
        }else if(count > 11){
            gotoRes();
        }
    }

    private void gotoRes() {
        Intent intent = new Intent(this,ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("a",a);
        bundle.putInt("b",b);
        bundle.putInt("c",c);
        bundle.putInt("d",d);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }

    public void ran(){
        t1 = (int)(Math.random()*8+1);
        t2 = (int)(Math.random()*9+1);
        t3 = (int)(Math.random()*5+1);
        t4 = (int)(Math.random()*6+1);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(this,WarningActivity.class));
            this.finish();
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){

            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){

            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

        }
        return false;
    }
}
