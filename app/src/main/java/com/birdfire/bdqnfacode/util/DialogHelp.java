package com.birdfire.bdqnfacode.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by C on 2016/11/17.
 */
public class DialogHelp {
    /**
     * 获取waiting Dialog
     */
    public static ProgressDialog getWaitDialog(Context context,String msg){
        ProgressDialog waitDlg = new ProgressDialog(context);
        if (!TextUtils.isEmpty(msg)) {
            waitDlg.setMessage(msg);
        }
        return  waitDlg;
    }

}
