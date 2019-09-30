package com.example.dictionary;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;

public class WordProvider1 extends ContentProvider {
    private final static String AUTHORITY = "com.example.dictionary.provider";
    private final static int WORDS_DIR = 0;
    private final static int WORDS_ITEM = 1;
    private Context context;
    private WordsDBHelper wordsDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "words", WORDS_DIR);
        uriMatcher.addURI(AUTHORITY, "words/#", WORDS_ITEM);
    }

    @Override
    public boolean onCreate() {
        wordsDBHelper = new WordsDBHelper(getContext(), "words", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        sqLiteDatabase = wordsDBHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case WORDS_DIR:
                cursor = sqLiteDatabase.query("words", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WORDS_ITEM:
                String word = uri.getPathSegments().get(2);
                cursor = sqLiteDatabase.query("words", projection, "word = ? ", new String[]{word}, null, null, sortOrder);
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues conentValues) {
        sqLiteDatabase = wordsDBHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case WORDS_DIR:
            case WORDS_ITEM:
                long newWordId = sqLiteDatabase.insert("words", null, conentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/words/" + newWordId);
                break;
            default:
                break;
        }
        return uriReturn;


    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        sqLiteDatabase = wordsDBHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORDS_DIR:
                updateRows = sqLiteDatabase.update("words", contentValues, selection, selectionArgs);
                break;
            case WORDS_ITEM:
                String word = uri.getPathSegments().get(2);
                updateRows = sqLiteDatabase.update("words", contentValues, "words = ? ", new String[]{word});
                break;
            default:
                break;
        }
        return updateRows;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqLiteDatabase = wordsDBHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORDS_DIR:
                deleteRows = sqLiteDatabase.delete("words", selection, selectionArgs);
                break;
            case WORDS_ITEM:
                String word = uri.getPathSegments().get(2);
                deleteRows = sqLiteDatabase.delete("words", "word = ? ", new String[]{word});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType (Uri uri){
        switch (uriMatcher.match(uri)){
            case WORDS_DIR:
                return "vnd.android.cursor.dir/vnd."+AUTHORITY+".words";
            case WORDS_ITEM:
                return "vnd.android.cursor.item/vnd."+AUTHORITY+".words";
        }
        return null;
    }






}