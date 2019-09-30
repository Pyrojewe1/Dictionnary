package com.example.dictionary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private WordsDBHelper wordsDBHelper;
    private ArrayList<Word> wdl = new ArrayList<Word>();
    private ListView listView;
    public Fragment1 fragment1;



    public ListViewAdapter(Context context, ArrayList<Word> wdl, ListView listView, WordsDBHelper wordsDBHelper) {
        this.listView = listView;
        this.wordsDBHelper = wordsDBHelper;
        this.context = context;
        this.wdl = wdl;
    }

    @Override
    public int getCount() {
        return wdl.size();
    }

    @Override
    public Object getItem(int positon) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else
            view = View.inflate(context, R.layout.item, null);
        Word wd = wdl.get(position);
        if (wd == null) {
            wd = new Word("no", "no", "no");
        }
        final TextView wordTextView = (TextView) view.findViewById(R.id.word);
        wordTextView.setText(wd.getWord());
        final TextView meaningTextView = (TextView) view.findViewById(R.id.meaning);
        meaningTextView.setText(wd.getMeaning());
        final TextView sampleTextView = (TextView) view.findViewById(R.id.sample);
        sampleTextView.setText(wd.getSample());
        final int removePosition = position;
        Button buttondel = view.findViewById(R.id.buttondel);
        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("position", String.valueOf(position));
                //wdl.remove(position);
                SQLiteDatabase sqLiteDatabase = wordsDBHelper.getWritableDatabase();
                wordsDBHelper.delete(sqLiteDatabase, "words", wdl.get(position).getWord());
                wdl = wordsDBHelper.query(sqLiteDatabase, "words");
                listView.setAdapter(new ListViewAdapter(context, wdl, listView, wordsDBHelper));

            }
        });


        Button buttonedit = view.findViewById(R.id.buttonedit);
        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("position", String.valueOf(position));
                //wdl.remove(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("更改单词");
                final View view1 = LayoutInflater.from(context).inflate(R.layout.insertword, null);
                builder.setView(view1);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText wordtxt = (EditText) view1.findViewById(R.id.word);
                        final EditText meaningtxt = (EditText) view1.findViewById(R.id.meaning);
                        final EditText sampletxt = (EditText) view1.findViewById(R.id.sample);
                        Word wd = new Word(wordtxt.getText().toString(), meaningtxt.getText().toString(), sampletxt.getText().toString());
                        SQLiteDatabase sqLiteDatabase = wordsDBHelper.getWritableDatabase();
                        wdl = wordsDBHelper.query(sqLiteDatabase, "words");
                        wordsDBHelper.update1(sqLiteDatabase,"words",wd,wdl.get(position).getWord());
                        wdl = wordsDBHelper.query(sqLiteDatabase, "words");
                        listView.setAdapter(new ListViewAdapter(context, wdl, listView, wordsDBHelper));
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
                }
                });



                return view;
    }
}