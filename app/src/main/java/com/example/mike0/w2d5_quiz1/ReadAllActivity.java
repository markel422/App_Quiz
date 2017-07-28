package com.example.mike0.w2d5_quiz1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mike0.w2d5_quiz1.FeedReaderContract.FeedEntry;

public class ReadAllActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private DBHelper helper;
    private SQLiteDatabase database;

    Button readBtn;

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all);

        helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        readBtn = (Button) findViewById(R.id.btn_read);
        readBtn.setOnClickListener(this);

        resultTV = (TextView) findViewById(R.id.tv_result);

        resultTV.setText("Press Button to display results!");
    }

    private void readRecord() {
        resultTV.setText("");
        String[] projection = {
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_CONTENT
        };
        /*
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";


        String[] selectionArg = {
                "Record title"
        };
        String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + "DESC";
        */

        Cursor cursor = database.query(
                FeedEntry.TABLE_NAME,        // TABLE
                projection,                  // Projection
                null,                        // Selection (WHERE)
                null,                        // Values for selection
                null,                        // Group by
                null,                        // Filters
                null                         // Sort order
        );
        while(cursor.moveToNext()) {
            StringBuilder dataResult = new StringBuilder(String.valueOf(resultTV.getText().toString()));
            String entryTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE));
            String entryContent = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_CONTENT));
            Log.d(TAG, " title: " +  entryTitle + " content: " + entryContent);
            resultTV.setText(dataResult.append(String.format(getString(R.string.lbl_result), entryTitle, entryContent)));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read:
                readRecord();
                break;
        }
    }
}
