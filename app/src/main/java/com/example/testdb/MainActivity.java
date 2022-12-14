package com.example.testdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.testdb.DutyExample.DutyExample;
import com.example.testdb.MyDuty.MyDuty;
import com.example.testdb.Login.AppConfig;
import com.example.testdb.Login.login;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager manager; // 네트워크 연결상태 확인.
    RelativeLayout rl_searchMain;
    ImageButton ib_AllDuties;
    Button btn_logout;
    RelativeLayout rl_MyDuties, rl_AllDuties;
    AppConfig appConfig;

    Integer loginIndex;
    String loginNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_searchMain = findViewById(R.id.rl_searchMain); // 검색필터
        rl_AllDuties = findViewById(R.id.rl_AllDuties); // 전체업무
        rl_MyDuties = findViewById(R.id.rl_MyDuties); // 나의 업무
        btn_logout = findViewById(R.id.btn_logout); // 로그아웃 버튼

        //Intent 값 받기
        Intent intent = getIntent();
        loginIndex = intent.getIntExtra("number",0); // 인덱스 값.
        loginNickName = intent.getStringExtra("nickName"); // 닉네임.

        checkNetworkState(); // 네트워크 연결상태 확인.

        MainSearch(); // 메인 검색 클릭 시,
        AllDuties(); // 전체업무 클릭 시,
        MyDuties(); // 나의업무 클릭 시,
        LogOut(); // 로그아웃 버튼 클릭 시,

    }

    // 나의 업무 클릭 시,
    private void MyDuties() {
        rl_MyDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 인덱스 값이 null = 0 이면 (로그인 하지 않으면)
                if(loginIndex == 0) {
                    Toast.makeText(getApplicationContext(), "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
                    // 로그인 인덱스 값이 null != 0 이면 (로그인 하면,
                } else {
                    Intent intent = new Intent(getApplicationContext(), MyDuty.class);
                    intent.putExtra("loginIndex",loginIndex); // 로그인 인덱스 값.
                    intent.putExtra("loginNickName",loginNickName); // 로그인 닉네임 값.

                    startActivity(intent);
                }
            }
        });
    }

    // 로그아웃 버튼
    private void LogOut() {

        appConfig = new AppConfig(this); // 자동 로그인.

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 자동 로그인 해제 -> 다시 메인화면으로 돌아가기.
                appConfig.updateUserLoginStatus(false);
                startActivity(new Intent(MainActivity.this, login.class));
                finish();
            }
        });
    }

    // 검색 클릭 시,
    private void MainSearch() {

    }

    // 전체업무 보기
    private void AllDuties() {
        rl_AllDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DutyExample.class));
            }
        });
    }

    // 네트워크 연결 확인
    private void checkNetworkState() {
        manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        manager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                // 네트워크를 사용할 준비가 되었을 때
                Toast.makeText(getApplicationContext(), "네트워크 연결", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(@NonNull Network network) {
                // 네트워크가 끊겼을 때
                Toast.makeText(getApplicationContext(), "네트워크 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}