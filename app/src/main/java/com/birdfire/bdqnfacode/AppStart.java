package com.birdfire.bdqnfacode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.birdfire.bdqnfacode.ui.MainActivity;

public class AppStart extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        findViewById(R.id.app_start_view).postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        },1000);
    }

    /**
     * goto main
     */


    private void toMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
