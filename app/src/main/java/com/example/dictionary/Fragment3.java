package com.example.dictionary;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Fragment3 extends Fragment {
    public View view,view2;
    public TextView textView1,textView2;
    private ListView listview;
    private ArrayList<Word>items;
    public WordsDBHelper wordsDBHelper;
    public SQLiteDatabase db;
    Fragment3(TextView textView1,TextView textView2 , WordsDBHelper wordsDBHelper){
        this.textView1=textView1;
        this.textView2=textView2;
        this.wordsDBHelper=wordsDBHelper;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3,container,false);

        listview = (ListView)view.findViewById(R.id.listviewword);
        registerForContextMenu(listview);
        //Display dis = getWindowManager().getDefaultDisplay();
        items =new ArrayList<Word>();
        items = wordsDBHelper.query(db,"words");
        setadapterr(items);
        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items = wordsDBHelper.query(db,"words");
                setadapterr(items);
            }
        });
        return view;
    }

    void setadapterr(ArrayList<Word> items){
        Fragment3Adapter adapter = new Fragment3Adapter(view.getContext(),items,listview,wordsDBHelper,textView1,textView2);
        listview.setAdapter(adapter);

    }


}



