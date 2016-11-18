package com.birdfire.bdqnfacode.ui.dialog;

import android.app.ProgressDialog;

/**
 * Created by C on 2016/11/17.
 */
public interface DialogControl {
    public abstract void hideWaitDialog();
    public abstract ProgressDialog showWaitDialog();
    public abstract ProgressDialog showWaitDialog(int resid);
    public abstract ProgressDialog showWaitDialog(String text);
}
