package com.custompasswordinputbox;

import android.os.Bundle;

import com.inputbox.passwordinputbox.PasswordInputBox;
import com.fubang.custompasswordinputbox.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PasswordInputBox viewById = findViewById(R.id.pib);
        viewById.setCommitListener(new PasswordInputBox.CommitListener() {
            @Override
            public void commitListener(String content) {

            }
        });
    }
}
