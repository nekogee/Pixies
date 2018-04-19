package com.nekogee.pixies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hui jie on 2018/3/29.
 */

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
        final TextInputEditText textInputEditText = findViewById(R.id.editText_input);
        Button button = findViewById(R.id.button_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String data = textInputEditText.getText().toString();
                intent.putExtra("EditText",data);
                Log.d("neww", data);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
