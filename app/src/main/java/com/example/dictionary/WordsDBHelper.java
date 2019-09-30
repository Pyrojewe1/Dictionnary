package com.example.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.facebook.stetho.Stetho;
import java.util.ArrayList;

public class WordsDBHelper extends SQLiteOpenHelper {
    private Context context;//上下文
    private SQLiteDatabase sqLiteDatabase;
    private final static String DATABASE_NAME = "wordsdb";
    private final static int DATABASE_VERSION = 1;
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS words";

    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public WordsDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        Stetho.initializeWithDefaults(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);

    }


    public void insert(Word wd) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", wd.getWord());
        values.put("meaning", wd.getMeaning());
        values.put("sample", wd.getSample());
        db.insert("words", null, values);
    }

    public void delete(SQLiteDatabase sqLiteDatabase, String tableName, String wd) {
        String del = "delete from " + "'" + tableName + "'" + "where word = " + "'" + wd + "'";
        sqLiteDatabase.execSQL(del);
    }

    public void update1(SQLiteDatabase sqLiteDatabase, String tableName, Word wd, String word) {
        String update = "update " + tableName + " set word = ?, meaning = ?, sample = ? where word = ?";
        sqLiteDatabase.execSQL(update, new String[]{wd.getWord(), wd.getMeaning(), wd.getSample(), word});
    }

    public ArrayList<Word> query2(SQLiteDatabase sqLiteDatabase, String tableName, String queryword) {
        ArrayList<Word> wdlist = new ArrayList<Word>();
        //String sql2 = "select * from "+tableName+" where word like '%"+queryword+"%'";
        Cursor cursor2 = sqLiteDatabase.query(tableName, null, String.format("word like '%%%s%%'", queryword), null, null, null, null);
        Log.i("queryword", queryword);
        Log.i("queryword selection", String.format("word like '%%%s%%'", queryword));
        if (cursor2.moveToFirst()) {
            do {
                Word wd = new Word();
                String word = cursor2.getString(cursor2.getColumnIndex("word"));
                String meaning = cursor2.getString(cursor2.getColumnIndex("meaning"));
                String sample = cursor2.getString(cursor2.getColumnIndex("sample"));
                Log.i("word here111", word);
                Log.i("meaning here111 ", meaning);
                Log.i("sample here111", sample);
                wd.setWord(word);
                wd.setMeaning(meaning);
                wd.setSample(sample);
                wdlist.add(wd);
            }
            while (cursor2.moveToNext());
        }
        Log.i("heeeeeeeee2", String.valueOf(cursor2.getCount()));
        cursor2.close();
        return wdlist;

    }

    public ArrayList<Word> query(SQLiteDatabase sqLiteDatabase, String tableName) {
        sqLiteDatabase = getReadableDatabase();
        ArrayList<Word> wdlist = new ArrayList<Word>();
        Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Word wd = new Word();
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
                String sample = cursor.getString(cursor.getColumnIndex("sample"));
                Log.i("word here", word);
                Log.i("meaning here ", meaning);
                Log.i("sample here", sample);
                wd.setWord(word);
                wd.setMeaning(meaning);
                wd.setSample(sample);
                wdlist.add(wd);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return wdlist;

    }

}
