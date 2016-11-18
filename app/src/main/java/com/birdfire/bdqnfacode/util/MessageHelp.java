package com.birdfire.bdqnfacode.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.birdfire.bdqnfacode.service.MsgService;

import java.util.List;

/**
 * Created by C on 2016/11/17.
 */
public class MessageHelp {
    public static final int INTENT_NO_PRIORITY = -1;
    public static final int INTENT_MAX_PRIORITY = Integer.MAX_VALUE;
    public OnMsgServiceListener mlistener;
    private MsgBroadcastReceiver mReciver = new MsgBroadcastReceiver();
    MsgService mMsgService;
    private boolean mbregistered = false;

    public interface OnMsgServiceListener{
        void onAction(String action, Intent intent, BroadcastReceiver broadcastReceiver);
        void onMsgServiceConnected();
    }

    private class MsgBroadcastReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            MessageHelp.this.mlistener.onAction(action,intent,this);
        }
    }

    public void registerActions(Context context,List<String> actions,
                                int intentPriority,OnMsgServiceListener actionListener) {
        mlistener = actionListener;
        if (actions != null){
            IntentFilter intentFilter = new IntentFilter();
            if (intentPriority != INTENT_NO_PRIORITY){
                intentFilter.setPriority(intentPriority);
            }
            for (String action :actions){
                intentFilter.addAction(action);
            }
            context.registerReceiver(mReciver,intentFilter);
            mbregistered = true;
        }

    }

    public void unregisterActions(Context context)
    {
        if (mbregistered){
            context.unregisterReceiver(mReciver);
            mbregistered = false;
        }
    }

    public boolean bindService(Context contx){
        Intent intent = new Intent();
        intent.setClass(contx, MsgService.class);
        if(!contx.bindService(intent,mMsgConn, Context.BIND_AUTO_CREATE)){
            return false;
        }else{
            return  true;
        }
    }

    public void unbindService(Context cxt){
        cxt.unbindService(mMsgConn);
    }
    private ServiceConnection mMsgConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(mMsgService == null){
                MsgService.MsgServiceBinder  binder = ( MsgService.MsgServiceBinder)service;
                mMsgService = binder.getService();
                if (mMsgService == null)
                {
                    return;
                }
            }
            mlistener.onMsgServiceConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    public boolean connect(Context context, List<String> actions,
                           int intentPriority,OnMsgServiceListener actionListener){
        registerActions(context,actions,intentPriority,actionListener);
        return bindService(context);
    }
    public void disConnect(Context cxt){
        unregisterActions(cxt);
        unbindService(cxt);
    }
}
