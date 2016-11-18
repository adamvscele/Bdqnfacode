package com.birdfire.bdqnfacode;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by C on 2016/11/17.
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppmanager() {
        if (instance == null) {
            instance = new AppManager();
        }
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    public void addActivity(Activity act) {
        activityStack.add(act);
    }

    /**
     * 获取当前activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束指定Activity
     */
    public void finishActivity(Activity act) {
        if (act != null && activityStack.contains(act)) {
            activityStack.remove(act);
            act.finish();
        }
    }

    /**
     * 结束当前activity
     */
    public void finishActivity() {
        Activity act = activityStack.lastElement();
        finishActivity(act);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    /**
     * exit app
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}














