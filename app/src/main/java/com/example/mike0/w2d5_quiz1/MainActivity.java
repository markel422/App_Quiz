package com.example.mike0.w2d5_quiz1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText title;

    Button saveBtn, readAllBtn, updateBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.et_title);

        saveBtn = (Button)  findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);
        readAllBtn = (Button)  findViewById(R.id.btn_read);
        readAllBtn.setOnClickListener(this);
        updateBtn = (Button)  findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(this);
        deleteBtn = (Button)  findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String titleValue = title.getText().toString();
        switch (view.getId()) {
            case R.id.btn_save:
                Intent intent = new Intent(MainActivity.this, SaveActivity.class);
                intent.putExtra("saveData", titleValue);
                startActivity(intent);
                break;
            case R.id.btn_read:
                intent = new Intent(MainActivity.this, ReadAllActivity.class);
                intent.putExtra("readData", titleValue);
                startActivity(intent);
                break;
            case R.id.btn_update:
                intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("updateData", titleValue);
                startActivity(intent);
                break;
            case R.id.btn_delete:
                intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra("deleteData", titleValue);
                startActivity(intent);
                break;
        }
    }
}
