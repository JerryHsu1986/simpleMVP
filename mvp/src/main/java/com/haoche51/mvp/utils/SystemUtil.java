package com.haoche51.mvp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Button;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author HaiboXu on 2018/10/17
 * @github https://github.com/JerryHsu1986
 */
public class SystemUtil {

    private static String deviceId;

    /**
     * 网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager  connectivityManager = (ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetwork() != null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(scale*dp+0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @return
     */
    public static int px2dp(Context context, float px){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(px/scale +0.5f);
    }

    /**
     * 获取设备唯一id；android 6.0 开始， 必须要动态获取权限拿到deviceId；
     *  so 采用 AndroidId 和 Serial Number  tong 通用性更好。不受权限限制
     * @return
     */
    public static String getDeviceId (Context context){
        if (deviceId !=null){
            return deviceId;
        }
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    /**
     * 获取app 版本
     * @param context
     * @return
     */
    public static String getAppVersion(Context context){
        String version = "";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(),0);
            version = info.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return version;

    }

    /**
     * md5 字符串
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String toMD5(String text) throws NoSuchAlgorithmException{
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i =0;i<digest.length;i++){
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i]&0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}
