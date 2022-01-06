package com.example.showapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class showgridview extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showgridview);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);

        String[] data= new String[packages.size()];
        Drawable[] logos = new Drawable[packages.size()];
        String[] packagesname = new String[packages.size()];

        showgridview.AppInfo tmpInfo = new showgridview.AppInfo();

        int j =0;

        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);

            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  //第三方應用
                //appList.add(tmpInfo);
                //System.out.println(tmpInfo.appName+".");
                /*data[i]= tmpInfo.appName;
                logos[i]=tmpInfo.appIcon;
                packagesname[i]=tmpInfo.packageName;*/

                data[j]= tmpInfo.appName;
                logos[j]=tmpInfo.appIcon;
                packagesname[j]=tmpInfo.packageName;

                j+=1;
            }
            else if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){  //安裝在SDCard上的應用
                //appList.add(tmpInfo);
                //System.out.println(tmpInfo.appName+".");
                //data[i]= tmpInfo.appName;

                /*data[i]= tmpInfo.appName;
                logos[i]=tmpInfo.appIcon;
                packagesname[i]=tmpInfo.packageName;*/

                data[j]= tmpInfo.appName;
                logos[j]=tmpInfo.appIcon;
                packagesname[j]=tmpInfo.packageName;
                j+=1;
            }
            else if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {  //系統應用
                //appList.add(tmpInfo);
                //System.out.println(tmpInfo.appName+".");
                /*data[i]= tmpInfo.appName;
                logos[i]=tmpInfo.appIcon;
                packagesname[i]=tmpInfo.packageName;*/

               data[j]= tmpInfo.appName;
                logos[j]=tmpInfo.appIcon;
                packagesname[j]=tmpInfo.packageName;
                j+=1;
            }



        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));

        //adapter = new MyRecyclerViewAdapter(this, data);
        adapter = new MyRecyclerViewAdapter(this,data,logos,packagesname);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent goapp = getPackageManager().getLaunchIntentForPackage(adapter.getItempackagename(position));
        startActivity(goapp);
    }

    public class AppInfo {
        public String appName = "";
        public String packageName = "";
        public Drawable appIcon = null;
    }
}