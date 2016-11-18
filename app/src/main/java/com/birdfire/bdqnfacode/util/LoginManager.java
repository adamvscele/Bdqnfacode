package com.birdfire.bdqnfacode.util;

/**
 * Created by C on 2016/11/17.
 */
public class LoginManager extends MManager{
    private  static LoginManager mInstance;


    private String mLoginName;
    private String mLoginPwd;
    public static LoginManager Instance(){
        synchronized (LoginManager.class){
            if (mInstance == null){
                mInstance = new LoginManager();
            }
            return  mInstance;
        }
    }


    public void login(String name,String pws){

        mLoginName = name;
        mLoginPwd =pwd;
    }

    private void connectLoginServer(){

    }

}
