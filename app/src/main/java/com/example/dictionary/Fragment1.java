package com.example.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Fragment1 extends Fragment {
    public WordsDBHelper wordsDBHelper;
    private ArrayList<Word> items;
    private SQLiteDatabase db;
    public ListView listview;
    private Context context;
    public View view;
    private int a = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        listview = (ListView) view.findViewById(R.id.lv1);
        registerForContextMenu(listview);
        //Display dis = getWindowManager().getDefaultDisplay();
        items = new ArrayList<Word>();
        items = wordsDBHelper.query(db, "words");
        setadapterr(items);
        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items = wordsDBHelper.query(db, "words");
                setadapterr(items);
            }
        });
        context = view.getContext();
        a = 1;
        return view;
    }

    void setadapterr(ArrayList<Word> items) {
        ListViewAdapter adapter = new ListViewAdapter(view.getContext(), items, listview, wordsDBHelper);
        listview.setAdapter(adapter);

    }


}



