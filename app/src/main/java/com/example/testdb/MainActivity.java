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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.testdb.DutyExample.DutyExample;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager manager; // 네트워크 연결상태 확인.
    RelativeLayout rl_searchMain;
    ImageButton ib_AllDuties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkState(); // 네트워크 연결상태 확인.

        MainSearch(); // 메인 검색 클릭 시,
        AllDuties(); // 전체업무 클릭 시,

    }

    private void MainSearch() {
        rl_searchMain = findViewById(R.id.rl_searchMain);
    }

    private void AllDuties() {
        ib_AllDuties = findViewById(R.id.ib_allDutyList);
        ib_AllDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DutyExample.class);
                startActivity(intent);
            }
        });
    }

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