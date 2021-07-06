package com.tech;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatDelegate;
import com.tech.repository.DownloadRepository;
import com.tech.utils.Const;

public class MyApplication extends Application implements DownloadObserver.Callback {
    Handler handler = new Handler(Looper.getMainLooper());
    DownloadRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = DownloadRepository.getInstance(this);
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads/"),
                true, new DownloadObserver(handler, this));
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Const.NIGHT_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onChange(long id) {
        repository.updateDownload(id);
    }
}
