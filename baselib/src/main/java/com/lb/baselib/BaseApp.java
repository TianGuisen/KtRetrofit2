package com.lb.baselib;

import android.app.Application;

public abstract class BaseApp extends Application {

    /*
    * application初始化
     */
    public abstract void initModuleApp(Application application);

}
