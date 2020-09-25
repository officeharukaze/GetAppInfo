package net.harukaze.app.getappinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.harukaze.app.getappinfo.MyAppInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    PackageManager pm = null;

    ListView listView1;
    static List<MyAppInfo> dataList = new ArrayList<MyAppInfo>();
    static PackageListAdapter adapter = null;

    Button bt;
    Spinner spEval;
    Spinner spInstNum;

    String spEvalItem;
    String spInstNumItem;

    String[] selectEvals = new String[]{"全件", "動画アプリ", "SYSTEM", "SYSTEM以外"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.button);
        spEval = (Spinner) findViewById(R.id.spinner1);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            spEvalItem = (String) spEval.getSelectedItem();
            updatePackageList(spEvalItem);
            }
        });

        pm = getApplicationContext().getPackageManager();

        listView1 = (ListView) findViewById(R.id.listView1);

        adapter = new PackageListAdapter();
        listView1.setAdapter(adapter);

        updatePackageList(selectEvals[0]);
    }

    //アプリ一覧表示
    protected void updatePackageList(String selectEv){
        dataList.clear();
        List<PackageInfo> pkgInfoList = pm.getInstalledPackages(0);

        for(PackageInfo pkgInfo : pkgInfoList){
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            String sourceDir = appInfo.publicSourceDir;
            String eval = selectEvals[0]; // 全件用

            if(pkgInfo.packageName.startsWith("com.google.android.youtube") ||
              pkgInfo.packageName.startsWith("jp.unext") ||
              pkgInfo.packageName.startsWith("tv.abema") ||
              pkgInfo.packageName.startsWith("jp.happyon.android") || // Hulu
              pkgInfo.packageName.startsWith("com.netflix") ||
              pkgInfo.packageName.startsWith("com.amazon") || // Prime Video
              pkgInfo.packageName.startsWith("jp.co.yahoo.gyao")||
              pkgInfo.packageName.startsWith("com.dazn") ||
              pkgInfo.packageName.startsWith("com.dmm") ||
              pkgInfo.packageName.startsWith("jp.co.disney") ||
              pkgInfo.packageName.startsWith("air.jp.co.fujitv") ||
              pkgInfo.packageName.startsWith("jp.co.rakuten.video") ||
              pkgInfo.packageName.startsWith("com.kddi") ||
              pkgInfo.packageName.startsWith("jp.tmediahd") ||
              pkgInfo.packageName.startsWith("jp.tsutaya") ||
              pkgInfo.packageName.startsWith("jp.co.tver") ||
              pkgInfo.packageName.startsWith("jp.co.nttdocomo") ||
              pkgInfo.packageName.startsWith("com.bch.sp") // バンダイチャンネル
            ) {
                eval = selectEvals[1];
            }
            //フィルタリング条件分岐
            if(selectEvals[0].equals(selectEv)){
                String label = appInfo.loadLabel(pm).toString();
                Drawable drawable = appInfo.loadIcon(pm);
                String packageName = pkgInfo.packageName;
                String versionName = pkgInfo.versionName;
                Long lastUpdateTime = pkgInfo.lastUpdateTime;
                dataList.add(new MyAppInfo(sourceDir, label, drawable, eval, packageName, versionName, lastUpdateTime));
            }
            if(selectEvals[1].equals(selectEv) && eval.equals(selectEv)){
                String label = appInfo.loadLabel(pm).toString();
                Drawable drawable = appInfo.loadIcon(pm);
                String packageName = pkgInfo.packageName;
                String versionName = pkgInfo.versionName;
                Long lastUpdateTime = pkgInfo.lastUpdateTime;
                dataList.add(new MyAppInfo(sourceDir, label, drawable, eval, packageName, versionName, lastUpdateTime));
            }
            if(sourceDir.startsWith("/system/") && selectEvals[2].equals(selectEv)){
                eval = selectEvals[2];
                String label = appInfo.loadLabel(pm).toString();
                Drawable drawable = appInfo.loadIcon(pm);
                String packageName = pkgInfo.packageName;
                String versionName = pkgInfo.versionName;
                Long lastUpdateTime = pkgInfo.lastUpdateTime;
                dataList.add(new MyAppInfo(sourceDir, label, drawable, eval, packageName, versionName, lastUpdateTime));
            }
            if(!sourceDir.startsWith("/system/") && selectEvals[3].equals(selectEv)){
                eval = selectEvals[3];
                String label = appInfo.loadLabel(pm).toString();
                Drawable drawable = appInfo.loadIcon(pm);
                String packageName = pkgInfo.packageName;
                String versionName = pkgInfo.versionName;
                Long lastUpdateTime = pkgInfo.lastUpdateTime;
                dataList.add(new MyAppInfo(sourceDir, label, drawable, eval, packageName, versionName, lastUpdateTime));
            }
        }

        Collections.sort(dataList, new MyAppInfoComparator());

        adapter.notifyDataSetChanged();
    }


    private class PackageListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(
                int position,
                View convertView,
                ViewGroup parent) {

            View v = convertView;
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;
            ImageView imageView1;

            if(v==null){
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(
                                Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.row, null);
            }

            MyAppInfo aInfo = (MyAppInfo)getItem(position);
            if(aInfo != null){
                textView1 = (TextView) v.findViewById(R.id.textView1);
                textView2 = (TextView) v.findViewById(R.id.textView2);
                textView3 = (TextView) v.findViewById(R.id.textView3);
                textView4 = (TextView) v.findViewById(R.id.textView4);
                imageView1 = (ImageView) v.findViewById(R.id.imageView1);

                textView1.setText(aInfo.getLabel());
                textView2.setText(aInfo.getPackageName());
                textView3.setText(aInfo.getVersionName());
                Timestamp timestamp = new Timestamp(aInfo.getLastUpdateTime());
                textView4.setText("" +  timestamp);
                imageView1.setImageDrawable(aInfo.getDrawable());
            }
            return v;
        }

    }

}