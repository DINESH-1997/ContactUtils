package com.example.contactutils.utils;

import static com.example.contactutils.utils.GlobalValues.SHARED_PREF_NAME;

import android.content.Context;

public class SharedPreferences {

    private static SharedPreferences instance;
    private static Context mContext;
    private static android.content.SharedPreferences sharedPreferences;

    public static SharedPreferences getInstance(Context context){
        if(instance == null){
            mContext = context;
            instance= new SharedPreferences();
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void writeToSharedPreference(String key, String value) {
        //android.content.SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String readFromSharedPreference(String key) {
        String value = "";
        //android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(key, "");
        return value;
    }

    public void removeFromSharedPreference(String key) {
        //android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
