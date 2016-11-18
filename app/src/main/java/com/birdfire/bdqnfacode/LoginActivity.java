package com.birdfire.bdqnfacode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.birdfire.bdqnfacode.base.BaseActivity;
import com.birdfire.bdqnfacode.base.BaseActivityNobar;
import com.birdfire.bdqnfacode.common.ErrorCode;
import com.birdfire.bdqnfacode.config.SysConstant;
import com.birdfire.bdqnfacode.log.Logger;
import com.birdfire.bdqnfacode.ui.MainActivity;
import com.birdfire.bdqnfacode.util.LoginManager;
import com.birdfire.bdqnfacode.util.MessageActions;
import com.birdfire.bdqnfacode.util.MessageHelp;
import com.birdfire.bdqnfacode.util.ServerResClient;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by C on 2016/11/17.
 */
public class LoginActivity extends BaseActivityNobar implements MessageHelp.OnMsgServiceListener{

    private static Logger mlogger = Logger.getLogger(LoginActivity.class);
    private EditText mNameEdt;
    private EditText mPwdEdt;
    private String mLginName;
    //private MessageHelp mMsgHelp = new MessageHelp();

    private LoginManager mLoginManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private String getLoginError(int errorCode) {
        switch (errorCode) {
            case ErrorCode.E_CONNECT_LOGIN_SERVER_FAILED:
                return getString(R.string.connect_login_failed);
            case ErrorCode.E_LOGIN_IDENTITY_FAILED:
                return getString(R.string.connect_login_identity_failed);
            default:
                return getString(R.string.connect_login_unexpected);
        }
    }

    private void onLoginError(int err) {
        String errTip = getLoginError(err);
        Toast.makeText(this, errTip, Toast.LENGTH_LONG).show();
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private TextHttpResponseHandler mrespondHandler = new TextHttpResponseHandler() {
        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            mlogger.i("onFailure");
            mSweetDlg.hide();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            mlogger.i("onSuccess");
            StringBuilder builder = new StringBuilder();
            for (Header h :headers){
                mlogger.i(h.getName());

            }
            mSweetDlg.hide();
            //hideWaitDialog();
            mlogger.i(s);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //List<String> actions = new ArrayList<String>();
       // actions.add(MessageActions.ACTION_LOGIN_RESULT);
       // if (!mMsgHelp.connect(getApplicationContext(), actions, MessageHelp.INTENT_MAX_PRIORITY, this)) {

       // }

        setContentView(R.layout.activity_login);

        mNameEdt = (EditText) findViewById(R.id.name);
        mPwdEdt = (EditText) findViewById(R.id.password);
        mPwdEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                goLogin();
                return true;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        goLogin();
        mSweetDlg.dismiss();

        //showWaitDialog("waiting");
    }

    public void goLogin() {
      //  "{\"status\":%d, \"msg\":{\"sql\":\"%s\"}}", 0,
       //         tmpString);
        JSONObject jsob = new JSONObject();
        try {
            jsob.put("status",0);
            JSONObject jsob2 = new JSONObject();
            jsob2.put("sql","select * from alarm");
            jsob.put("msg",jsob2);
            StringEntity se = new StringEntity(jsob.toString());
            ServerResClient.post(this,"http://180.76.140.196:8081/jfm/DBO/doDBOExcSql.action",se,"application/json",mrespondHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /*
        mLginName = mNameEdt.getText().toString().trim();
        String pwd = mPwdEdt.getText().toString().trim();
        boolean bcancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(pwd)) {
            bcancel = true;
            focusView = mPwdEdt;
        }
        if (TextUtils.isEmpty(mLginName)) {
            bcancel = true;
            focusView = mNameEdt;
        }
        if (bcancel) {
            focusView.requestFocus();
        } else {
            showWaitDialog();
            if (mLoginManager != null) {


            }
        }*/

    }

    @Override
    public void onAction(String action, Intent intent, BroadcastReceiver broadcastReceiver) {
        if (action.equals(MessageActions.ACTION_LOGIN_RESULT)) {
            int errcode = intent.getIntExtra(
                    SysConstant.LOGIN_ERR_CODE, -1);
            if (errcode != ErrorCode.S_OK) {
                onLoginError(errcode);
            } else {
                onLoginSuccess();
            }

        }
    }

    @Override
    public void onMsgServiceConnected() {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.birdfire.bdqnfacode/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.birdfire.bdqnfacode/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
