package com.example.showapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class showgridview extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showgridview);

        pm = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        ArrayList<String> data= new ArrayList<>();
        ArrayList<Drawable> logos = new ArrayList<>();
        ArrayList<String> packagesname = new ArrayList<>();

        for (ApplicationInfo applicationInfo : applicationInfos) {
            String appName = applicationInfo.loadLabel(pm).toString();// 应用名
            String packageName = applicationInfo.packageName;// 包名
            Drawable appIcon = applicationInfo.loadIcon(pm);

            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  //第三方應用

                data.add(appName) ;
                logos.add(appIcon);
                packagesname.add(packageName);
            }
            else if (applicationInfo.flags == ApplicationInfo.FLAG_SYSTEM)  {  //SDCard应用

                data.add(appName) ;
                logos.add(appIcon);
                packagesname.add(packageName);
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
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_showgridview);

        pm = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        ArrayList<String> data= new ArrayList<>();
        ArrayList<Drawable> logos = new ArrayList<>();
        ArrayList<String> packagesname = new ArrayList<>();

        for (ApplicationInfo applicationInfo : applicationInfos) {
            String appName = applicationInfo.loadLabel(pm).toString();// 应用名
            String packageName = applicationInfo.packageName;// 包名
            Drawable appIcon = applicationInfo.loadIcon(pm);

            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  //第三方應用

                data.add(appName) ;
                logos.add(appIcon);
                packagesname.add(packageName);
            }
            else if (applicationInfo.flags == ApplicationInfo.FLAG_SYSTEM)  {  //SDCard应用

                data.add(appName) ;
                logos.add(appIcon);
                packagesname.add(packageName);
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
    @Override
    public void onItemLongClick(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(showgridview.this);
        builder.setTitle(adapter.getItemname(position));
        builder.setMessage("確定要刪除該應用程式?");
        //設定Positive按鈕資料
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯
                Toast.makeText(showgridview.this, "您按下OK按鈕", Toast.LENGTH_SHORT).show();
                uninstallPackage(adapter.getItempackagename(position));
            }
        });
        //設定Negative按鈕資料
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯
                ;
            }
        });
        //利用Builder物件建立AlertDialog
        builder.create();
        builder.show();
    }

    private void uninstallPackage(String packageName) {
        Intent intent = new Intent(this, this.getClass());
        PendingIntent sender = PendingIntent.getActivity(this, 0, intent, 0);
        PackageInstaller mPackageInstaller = this.getPackageManager().getPackageInstaller();
        mPackageInstaller.uninstall(packageName, sender.getIntentSender());
    }


}