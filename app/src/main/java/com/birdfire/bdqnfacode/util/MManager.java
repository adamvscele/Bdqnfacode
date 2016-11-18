package com.birdfire.bdqnfacode.util;

import android.content.Context;

/**
 * Created by C on 2016/11/17.
 */
public class MManager {
    protected Context mcxt;
    public  void setContext(Context cxt){
        if (cxt == null){
            throw  new RuntimeException("null context");
        }
        mcxt = cxt;
    }

}
