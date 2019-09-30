package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public   WordsDBHelper wordsDBHelper;
    private   ArrayList<Word> items;
    private SQLiteDatabase db;
    private  ListView listview;
    private Fragment1 fragment1;
    public TextView textView1,textView2;
    public Fragment3 fragment3;
    public  int bool =0;   //0竖1横


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            // 切换成竖屏
            setContentView(R.layout.activity_main);
            // findViewById
            Fragment1 fragment11 = new Fragment1();
            fragment11.wordsDBHelper = wordsDBHelper;
            fragment1=fragment11;
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.left,fragment1);
            ft.commit();
            bool = 0 ;


            // 进行一些操作。。。
        } else {
            // 切换成横屏
            setContentView(R.layout.activity2);
            textView1 = findViewById(R.id.textmeaing);
            textView2 = findViewById(R.id.textsample);
            Fragment3 fragment33 = new Fragment3(textView1,textView2,wordsDBHelper);
            fragment3 = fragment33;
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.fragment3,fragment3);
            ft.commit();
            bool = 1 ;

        }
        // 在这里添加屏幕切换后的操作
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //导入菜单布局
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.addword:
                Toast.makeText(this, "添加单词", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("请输入单词");
                final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.insertword, null);
                builder.setView(view1);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        final EditText wordtxt = (EditText)view1.findViewById(R.id.word);
                        final EditText meaningtxt = (EditText)view1.findViewById(R.id.meaning);
                        final EditText sampletxt = (EditText)view1.findViewById(R.id.sample);
                        Word wd = new Word(wordtxt.getText().toString(),meaningtxt.getText().toString(),sampletxt.getText().toString());
                        wordsDBHelper.insert(wd);
                        items = wordsDBHelper.query(db,"words");
                        if(bool == 0 )
                        fragment1.setadapterr(items);
                        else
                            fragment3.setadapterr(items);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.show();
                break;
            case R.id.queryword:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("查询单词");
                final View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.queryword,null);
                builder1.setView(view2);
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText editText = (EditText) view2.findViewById(R.id.word);
                        String queryword = editText.getText().toString();
                        ArrayList<Word> queryResult = wordsDBHelper.query2(db,"words",queryword);
                        if(bool == 0)
                        fragment1.setadapterr(queryResult);
                        else fragment3.setadapterr(queryResult);
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder1.show();
                Toast.makeText(this, "查询单词", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new Fragment1();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.left,fragment1);
        ft.commit();
        wordsDBHelper = new WordsDBHelper(this,"words",null,1) ;
        fragment1.wordsDBHelper = wordsDBHelper;
        Log.d("test null", String.valueOf(wordsDBHelper == null));
        db=wordsDBHelper.getWritableDatabase();

    }




}
