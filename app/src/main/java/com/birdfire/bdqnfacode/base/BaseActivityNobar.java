package com.birdfire.bdqnfacode.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.birdfire.bdqnfacode.AppManager;
import com.birdfire.bdqnfacode.R;
import com.birdfire.bdqnfacode.ui.dialog.DialogControl;
import com.birdfire.bdqnfacode.util.DialogHelp;

/**
 * Created by C on 2016/11/17.
 */
public class BaseActivityNobar extends Activity implements
        DialogControl {
    protected ViewGroup mtopContext;
    protected ViewGroup mtopBar;
    protected TextView mtopTitleTxt;
    protected ImageView mtopRightBtn;
    private boolean _isVisible;
    private ProgressDialog _waitDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppmanager().finishActivity(this);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mtopContext = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        mtopBar = (ViewGroup) mtopContext.findViewById(R.id.topbar);
        mtopTitleTxt = (TextView) mtopContext.findViewById(R.id.base_activity_title);
        mtopRightBtn = (ImageView) mtopContext.findViewById(R.id.right_btn);
        mtopTitleTxt.setVisibility(View.GONE);
        mtopRightBtn.setVisibility(View.GONE);
        _isVisible = true;
        AppManager.getAppmanager().addActivity(this);
    }


    protected void setTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        mtopTitleTxt.setText(title);
        mtopTitleTxt.setVisibility(View.VISIBLE);
    }

    protected void setRightButton(int resID) {
        if (resID <= 0) {
            return;
        }
        mtopRightBtn.setImageResource(resID);
        mtopTitleTxt.setVisibility(View.VISIBLE);
    }

    protected void setTopBar(int resID) {
        if (resID <= 0) {
            return;
        }
        mtopBar.setBackgroundResource(resID);
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String text) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, text);

            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(text);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return  null;
    }
}
