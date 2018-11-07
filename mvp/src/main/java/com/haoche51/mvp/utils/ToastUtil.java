package com.haoche51.mvp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author HaiboXu on 2018/10/17
 * @github https://github.com/JerryHsu1986
 */
public class ToastUtil {

    public static Toast appToast;

    public static Toast getInstance(Context context){
        if (appToast == null){
            appToast = Toast.makeText(context,"",Toast.LENGTH_LONG);
        }
        return appToast;
    }

    public static void showToast(String msg){
        if (appToast == null) return ;
        appToast.setText( msg);
        appToast.show();
    }
    public static void cancel(){
        if (appToast == null) return ;
        appToast.cancel();
    }

}
