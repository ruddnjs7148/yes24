package com.wony.kotech.androidyes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mPasswordView, mUserName;
    private CheckBox mCheckBox;
    public TextView mServicetxt, mPrivacytxt;
    public TextView mTextview;
    private View mProgressView;
    private View mLoginFormView;

    public WebView webView;

    static int a;
    private String id;
    private String pwd;

    private SharedPreferences appData;
    private boolean saveLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        // Set up the login form.
        mUserName = (EditText) findViewById(R.id.txtUserName);
        mPasswordView = (EditText) findViewById(R.id.txtPassword);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mServicetxt = (TextView) findViewById(R.id.txtService);
        mPrivacytxt = (TextView) findViewById(R.id.txtPrivacy);

        mServicetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("http://m.naver.com");
            }
        });

        mPrivacytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("http://m.daum.net");
            }
        });

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            mUserName.setText(id);
            mPasswordView.setText(pwd);
            mCheckBox.setChecked(saveLoginData);
        }

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                mTextview = (EditText) findViewById(R.id.numbertxt);
                a = Integer.parseInt(mTextview.getText().toString());

                String UserName = mUserName.getText().toString();
                String Pwd = mPasswordView.getText().toString();

                if(UserName.equalsIgnoreCase("ruddnjs7148@kotech.co.kr") && Pwd.equals("yes24")) {
                    save();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "잘못된 아이디 이거나 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", mCheckBox.isChecked());
        editor.putString("ID", mUserName.getText().toString().trim());
        editor.putString("PWD", mPasswordView.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
 }

