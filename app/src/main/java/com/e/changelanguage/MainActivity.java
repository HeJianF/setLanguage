package com.e.changelanguage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int LANGUAGE_DEFAULT = 0; //系统默认语言

    private final int LANGUAGE_ENGLISH = 1; //英语

    Button btEnglish, btChinese;
    Button btAnotherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //读取存储的语言数据，若不存在则设置为默认的语言
        SharedPreferences preferences = getSharedPreferences("language",MODE_PRIVATE);
        int a = preferences.getInt("language",LANGUAGE_DEFAULT);
        changeLanguage(a);
        setContentView(R.layout.activity_main);
        initFindView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_to_english:
                startIntent(LANGUAGE_ENGLISH);
                finish();
                break;
            case R.id.bt_default:
                startIntent(LANGUAGE_DEFAULT);
                finish();
                break;
            case R.id.bt_another_activity:
                Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
                startActivity(intent);
                break;
        }
    }

    //初始化
    private void initFindView() {
        btEnglish = (Button) findViewById(R.id.bt_to_english);
        btChinese = (Button) findViewById(R.id.bt_default);
        btAnotherActivity = (Button) findViewById(R.id.bt_another_activity);
        btEnglish.setOnClickListener(this);
        btChinese.setOnClickListener(this);
        btAnotherActivity.setOnClickListener(this);
    }

    //将已设置好的语言加载出来
    private void startIntent(int mode){
        changeLanguage(mode);
        Intent i1 = new Intent(this, MainActivity.class);
        startActivity(i1);
    }
    //切换语言
    private void changeLanguage(int mode) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (mode == LANGUAGE_ENGLISH) {
            configuration.locale = Locale.US;
            Log.d("执行", "changeLanguage: ");
        } else if (mode == LANGUAGE_DEFAULT) {
            configuration.locale = Locale.getDefault();
        }
        resources.updateConfiguration(configuration, metrics);
        //下次开机时语言还是设置的，持久化操作
        SharedPreferences.Editor editor = getSharedPreferences("language",MODE_PRIVATE).edit();
        editor.putInt("language",mode);
        editor.apply();
    }
}
