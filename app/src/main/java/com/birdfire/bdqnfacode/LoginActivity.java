package com.birdfire.bdqnfacode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.birdfire.bdqnfacode.base.BaseActivity;
import com.birdfire.bdqnfacode.common.ErrorCode;
import com.birdfire.bdqnfacode.config.SysConstant;
import com.birdfire.bdqnfacode.ui.MainActivity;
import com.birdfire.bdqnfacode.util.LoginManager;
import com.birdfire.bdqnfacode.util.MessageActions;
import com.birdfire.bdqnfacode.util.MessageHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C on 2016/11/17.
 */
public class LoginActivity extends BaseActivity implements MessageHelp.OnMsgServiceListener {
    private EditText mNameEdt;
    private EditText mPwdEdt;
    private String   mLginName;
    private MessageHelp mMsgHelp = new MessageHelp();
    private LoginManager mLoginManager;
    private String  getLoginError(int errorCode){
        switch (errorCode){
            case ErrorCode.E_CONNECT_LOGIN_SERVER_FAILED:
                return getString(R.string.connect_login_failed);
            case ErrorCode.E_LOGIN_IDENTITY_FAILED:
                return getString(R.string.connect_login_identity_failed);
            default:
                return getString(R.string.connect_login_unexpected);
        }
    }

    private void onLoginError(int err) {
        String errTip =getLoginError(err);
        Toast.makeText(this,errTip,Toast.LENGTH_LONG).show();
    }

    private void onLoginSuccess(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> actions = new ArrayList<String>();
        actions.add(MessageActions.ACTION_LOGIN_RESULT);
        if (!mMsgHelp.connect(getApplicationContext(),actions,MessageHelp.INTENT_MAX_PRIORITY,this)){

        }

        setContentView(R.layout.activity_login);
        mNameEdt = (EditText)findViewById(R.id.name);
        mPwdEdt =  (EditText)findViewById(R.id.password);
        mPwdEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                goLogin();
                return true;
            }
        });
    }

    public void goLogin(){
        mLginName = mNameEdt.getText().toString().trim();
        String pwd = mPwdEdt.getText().toString().trim();
        boolean bcancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(pwd)){
            bcancel = true;
            focusView = mPwdEdt;
        }
        if (TextUtils.isEmpty(mLginName)){
            bcancel = true;
            focusView = mNameEdt;
        }
        if (bcancel){
            focusView.requestFocus();
        }else
        {
            showWaitDialog();
            if (mLoginManager !=null){

                mLoginManager.
            }
        }

    }
    @Override
    public void onAction(String action, Intent intent, BroadcastReceiver broadcastReceiver) {
        if (action.equals(MessageActions.ACTION_LOGIN_RESULT)){
            int errcode = intent.getIntExtra(
                    SysConstant.LOGIN_ERR_CODE,-1);
            if (errcode != ErrorCode.S_OK){
                onLoginError(errcode);
            }else {
                onLoginSuccess();
            }

        }
    }

    @Override
    public void onMsgServiceConnected() {

    }
}
