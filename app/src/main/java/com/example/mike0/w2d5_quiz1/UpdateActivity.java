package com.example.mike0.w2d5_quiz1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike0.w2d5_quiz1.FeedReaderContract.FeedEntry;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private DBHelper helper;
    private SQLiteDatabase database;

    EditText title;
    EditText content;

    Button updateBtn;

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        title = (EditText) findViewById(R.id.et_title);
        content = (EditText) findViewById(R.id.et_content);

        updateBtn = (Button) findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(this);

        resultTV = (TextView) findViewById(R.id.tv_result);

        resultTV.setText("Title does not exist or typed correctly at Home Page. Please go back and enter an existing Title to be updated.");

        Intent intent = getIntent();

        if (intent != null) {
            String titleValue;

            titleValue = intent.getStringExtra("updateData");

            String[] projection = {
                    FeedEntry.COLUMN_NAME_TITLE,
                    FeedEntry.COLUMN_NAME_CONTENT
            };

            String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
            String[] selectionArgs = {
                    titleValue
            };

            Cursor cursor = database.query(
                    FeedEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
                    );

            while (cursor.moveToNext()) {
                String entryTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE));
                String entryContent = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_CONTENT));

                resultTV.setText("Old Title: " + entryTitle + " Old Content: " + entryContent);
            }
        }
    }

    private void updateRecord() {
        Intent intent = getIntent();
        String oldTitleValue;

        oldTitleValue = intent.getStringExtra("updateData");

        String newTitleValue = title.getText().toString();
        String contentValue = content.getText().toString();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, newTitleValue);
        values.put(FeedEntry.COLUMN_NAME_CONTENT, contentValue);

        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {
                oldTitleValue
        };

        int count = database.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );


        if (count > 0) {
            Log.d(TAG, "updateRecord: Updated records. " +  "(" + count + ")");
            Toast.makeText(this, "updateRecord: Updated records.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "updateRecord: Records not updated.");
            Toast.makeText(this, "updateRecord: Records not updated.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                updateRecord();
                break;
        }
    }
}
