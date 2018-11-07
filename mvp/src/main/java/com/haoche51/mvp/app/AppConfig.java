package com.haoche51.mvp.app;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author HaiboXu on 2018/10/17
 * @github https://github.com/JerryHsu1986
 */
public class AppConfig {
    private static Set<Activity> activities;

    /**
     * kill activity
     * @param activityClass
     */
    public static void killActivity(Activity activityClass){
        if (activities == null) return ;
        for (Activity activity:activities){
            if (activity.equals(activityClass)){
                activities.remove(activityClass);
                activity.finish();
            }
        }
    }

    /**
     * 添加activity
     * @param activityClass
     */
    public static void addActivity(Activity activityClass){
        if (activities == null){
            activities = new HashSet<Activity>();
        }
        activities.add(activityClass);
    }

    /**
     * 移出activity
     * @param activityClass
     */
    public static void removeActivity(Activity activityClass){
        if (activities !=null) activities.remove(activityClass);
    }

    /**
     * 退出app
     */
    public static void exitApp(){
        if (activities == null) return ;
        synchronized (activities){
            for (Activity activity:activities){
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
