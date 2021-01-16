package com.videoapp.AppActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.videoapp.R;
import com.videoapp.ServerConnector;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;
    private Button loginButton;
    private Button backButton;
    private Context context = LoginActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Intent i = new Intent(this, MainActivity.class);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int statusCode = 444;
                try {
                    statusCode = ServerConnector.login_URL(editUsername.getText().toString(), editPassword.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (statusCode == 200) {
                    setResult(2);
                    startActivity(i);
                    finish();
                } else if (statusCode == 400) {
                    ServerConnector.showAlert("Username or password field cannot be empty!", context);
                } else if (statusCode == 404) {
                    ServerConnector.showAlert("User not found. Try again.", context);
                } else if (statusCode == 444) {
                    ServerConnector.showAlert("Something went wrong while connecting to server.", context);
                } else if (statusCode == 511) {
                    ServerConnector.showAlert("Session has expired.", context);
                }
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editUsername = findViewById(R.id.login_username_edit);
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkColorEditText();
            }
        });

        editPassword = findViewById(R.id.login_password_edit);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkColorEditText();
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkColorEditText() {
        if (!TextUtils.isEmpty(editUsername.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())) {
            loginButton.setBackground(getResources().getDrawable(R.drawable.rounded_green_button));
        } else {
            loginButton.setBackground(getResources().getDrawable(R.drawable.rounded_grey_button));
        }
    }
}
