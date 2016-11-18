package com.birdfire.bdqnfacode.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.birdfire.bdqnfacode.log.Logger;

/**
 * Created by C on 2016/11/17.
 */
public class MsgService extends Service {

    private Logger logger = Logger.getLogger(MsgService.class);
    private MsgServiceBinder mBinder = new MsgServiceBinder();
    public class  MsgServiceBinder extends Binder{
        public MsgService getService(){return  MsgService.this;}
    }

    @Override
    public void onDestroy(){
        logger.i("MsgService onDestroy");
    }
    @Override
    public void onCreate() {
        logger.i("MsgService onCreate");
        super.onCreate();
        startForeground((int)System.currentTimeMillis(),new Notification());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        logger.i("MsgService onBind");
        return mBinder;
    }

}
