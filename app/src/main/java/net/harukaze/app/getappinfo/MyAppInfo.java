package net.harukaze.app.getappinfo;

import android.graphics.drawable.Drawable;

public class MyAppInfo {
    String sourceDir;
    String label;
    Drawable drawable;

    String eval;
    String packageName;
    String versionName;
    Long lastUpdateTime;

    public MyAppInfo(
            String sourceDir,
            String label,
            Drawable drawable,
            String eval,
            String packageName,
            String versionName,
            Long lastUpdateTime){
        this.sourceDir = sourceDir;
        this.label = label;
        this.drawable = drawable;
        this.eval = eval;
        this.packageName = packageName;
        this.versionName = versionName;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getSourceDir(){
        return this.sourceDir;
    }

    public String getLabel(){
        return this.label;
    }

    public Drawable getDrawable(){
        return this.drawable;
    }

    public String getEval(){
        return this.eval;
    }

    public String getPackageName(){
        return this.packageName;
    }

    public String getVersionName(){
        return this.versionName;
    }

    public long getLastUpdateTime(){
        return this.lastUpdateTime;
    }
}