package com.example.mike0.w2d5_quiz1;

import android.content.ContentValues;
import android.content.Intent;
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

public class SaveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private DBHelper helper;
    private SQLiteDatabase database;

    EditText titleET;
    EditText contentET;

    Button saveBtn;

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        titleET = (EditText) findViewById(R.id.et_title);
        contentET = (EditText) findViewById(R.id.et_content);

        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);

        resultTV = (TextView) findViewById(R.id.tv_result);

        Intent intent = getIntent();

        if (intent != null) {
            String titleValue;

            titleValue = intent.getStringExtra("saveData");

            titleET.setText(titleValue);
        }
    }

    private void saveRecord() {

        String title = titleET.getText().toString();
        String content = contentET.getText().toString();


        ContentValues values = new ContentValues(); // Prevents SQL injection
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedEntry.COLUMN_NAME_CONTENT, content);

        long recordId = database.insert(
                FeedEntry.TABLE_NAME,
                null,
                values
        );

        if (recordId > 0) {
            String titleValue, contentValue;
            Log.d(TAG, "Data Saved.");
            Toast.makeText(this, "Data saved.", Toast.LENGTH_SHORT).show();
            resultTV.setText("Title: " + title + " Content: " + content + " Saved!");
        } else  {
            Log.d(TAG, "Data Not Saved.");
            Toast.makeText(this, "Data Not Saved.", Toast.LENGTH_SHORT).show();
        }

        titleET.setText("");
        contentET.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveRecord();
                break;
        }
    }
}
