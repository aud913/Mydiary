package com.example.shin.mydiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Day extends AppCompatActivity {
    DB mDBHelper;
    String today;
    Cursor cursor;
    android.widget.SimpleCursorAdapter adapter;
    ListView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();

        mDBHelper = new DB(this, "Today.db", null, 1);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        cursor = db.rawQuery(
                "SELECT * FROM today ORDER BY date", null);

        adapter = new android.widget.SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, new String[] {
                "title", "date" }, new int[] { android.R.id.text1,
                android.R.id.text2 });

        ListView list = (ListView) findViewById(R.id.list1);
        list.setAdapter(adapter);

        mDBHelper.close();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
            case 1:
                if (resultCode == RESULT_OK) {
                    // adapter.notifyDataSetChanged();
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    cursor = db.rawQuery("SELECT * FROM today ORDER BY date", null);
                    adapter.changeCursor(cursor);
                    mDBHelper.close();
                }
                break;
        }
    }






}
