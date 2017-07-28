package com.example.mike0.w2d5_quiz1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mike0.w2d5_quiz1.FeedReaderContract.FeedEntry;

public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private DBHelper helper;
    private SQLiteDatabase database;

    Button deleteBtn;

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        deleteBtn = (Button) findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(this);

        resultTV = (TextView) findViewById(R.id.tv_result);
    }

    private void deleteRecord() {
        Intent intent = getIntent();
        String titleValue = intent.getStringExtra("deleteData");

        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {
                titleValue
        };
        int deleted = database.delete(
                FeedEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
        if (deleted > 0) {
            Log.d(TAG, "Record deleted.");
            Toast.makeText(this, "Record deleted.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "Record not deleted.");
            Toast.makeText(this, "Record not deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                deleteRecord();
                break;
        }
    }
}
