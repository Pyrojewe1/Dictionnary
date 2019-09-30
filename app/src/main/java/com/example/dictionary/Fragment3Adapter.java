package com.example.dictionary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class Fragment3Adapter extends BaseAdapter {
        private Context context;
        public TextView textView1,textView2;
        private WordsDBHelper wordsDBHelper;
        private ArrayList<Word> wdl = new ArrayList<Word>();
        private ListView listView;

    public Fragment3Adapter(Context context, ArrayList<Word> wdl, ListView listView, WordsDBHelper wordsDBHelper,TextView textView1,TextView textView2) {
        this.listView = listView;
        this.wordsDBHelper = wordsDBHelper;
        this.context = context;
        this.wdl = wdl;
        this.textView1 = textView1;
        this.textView2 = textView2;
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
            view = android.view.View.inflate(context, R.layout.fragment3item, null);
         Word wd = wdl.get(position);
        if (wd == null) {
            wd = new Word("no", "no", "no");
        }
        final Word wd1 = wd;
        TextView wordTextView = (TextView) view.findViewById(R.id.textView);
        wordTextView.setText(wd.getWord());
        final int removePosition = position;
        wordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView1.setText(wd1.getMeaning());
                textView2.setText(wd1.getSample());
            }
        });
        return view;
    }
    }

