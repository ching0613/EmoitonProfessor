package com.example.wen_cheng.emoitonprofessor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class WarningActivity extends AppCompatActivity {

    public AudioManager audioManager;
    public CallbackManager callbackManager;
    public LoginButton loginButton;
    private AccessToken accessToken;
    public String fb_name,fb_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FadeTransition();
        setContentView(R.layout.activity_warning);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        callbackManager = CallbackManager.Factory.create();
        FaceBookLogin();
    }

    /* publish_actions:發佈推文權限。
    *  AccessToken:登入狀態。
    *  link :取得fb個人頁面的連結。
    *
    *
    *
    * */

    private void FaceBookLogin() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setPublishPermissions("publish_actions");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FbGRrequest(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(WarningActivity.this,
                        "已取消",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(WarningActivity.this,
                        "登入發生問題",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FbGRrequest(LoginResult loginResult) {
        accessToken = loginResult.getAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        fb_name = object.optString("name");
                        fb_id = object.optString("id");
                        showToast_1();
                        wait_sec_500();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void FadeTransition() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(fade); //First Enter
        getWindow().setReenterTransition(fade); //Again Enter
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showToast_1() {
        View toastview = getLayoutInflater().inflate(R.layout.toast_white_bg_1,null);
        TextView textView = (TextView)toastview.findViewById(R.id.welcome);
        textView.setText("用戶" + "\t" + fb_name + "\t" + "已登入，歡迎。");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(toastview);
        toast.show();
    }

    public void wait_sec_500(){
        final Dialog dialog = ProgressDialog.show(WarningActivity.this,
                "", "",true);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    Thread.sleep(500);
                }
                catch(Exception e){
                    e.printStackTrace();
                }{
                    dialog.dismiss();
                    startActivity(new Intent(WarningActivity.this,
                            personalityActivity.class));
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){

            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){

            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

        }
        return false;
    }
}
