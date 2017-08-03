package kx.newsdemo;

import android.app.Application;
import android.content.Context;

import kx.newsdemo.api.RetrofitService;

/**
 * Created by 31716 on 2017/5/25.
 */

public class AndroidApplication extends Application{

    private static  Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        RetrofitService.init();
    }
    public static Context getContext() {
        return context;
    }
}
