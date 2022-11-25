package com.test.fcm_test;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SaveSharedPreference {

    private static final String PREFERENCES_NAME = "my_preferences";
    static final String PREF_USER_NAME = "username";

    public static SharedPreferences getPreferences(Context mContext){
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    // 저장된 정보 가져오기
    public static String getUserName(Context mContext) {
        return getPreferences(mContext).getString(PREFERENCES_NAME, "");
    }
    public static String getString(Context Context, String key) {
        SharedPreferences prefs = getPreferences(Context);
        return prefs.getString(key, "");
    }

    //삭제
    public static void clearPreferences(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.apply();
    }
    //삭제
    public static void clearPreferences1(Context context){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

    //저장
    public static void setLoginInfo(Context context, String email, String password){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }
    public static void setToken(Context context, String token){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static Map<String, String> getLoginInfo(Context context){
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> LoginInfo = new HashMap<>();
        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");

        LoginInfo.put("email", email);
        LoginInfo.put("password", password);

        return LoginInfo;
    }

    public static void findToken(Context context) {
        String email = getString(context, "email");

    }

/*

    static final String PREF_USER_NAME = "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }*/
}