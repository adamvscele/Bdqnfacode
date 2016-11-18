package com.birdfire.bdqnfacode.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.PublicKey;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by C on 2016/11/18.
 */
public class ServerResClient {
    //http://180.76.140.196:41235/jfm/DBO/doDBOExcSql.action
    private static  String BASE_URL ="http://180.76.140.196:8081/jfm/DBO/doDBOExcSql.action";
    private static AsyncHttpClient mclient = new AsyncHttpClient();
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler resphandler){
        mclient.get(getAbsoluteUrl(url),params,resphandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler resphandler){
        mclient.post(getAbsoluteUrl(url),params,resphandler);
    }
    public static void post(Context cxt, String url, StringEntity params,String type, AsyncHttpResponseHandler resphandler){
        mclient.post(cxt,url,params,type,resphandler);
    }

    private static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL +relativeUrl;
    }
}
