package com.example.testdb.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.testdb.DutyExample.DTO.MemberShip;
import com.example.testdb.MainActivity;
import com.example.testdb.R;
import com.example.testdb.Retrofit.GetDataService;
import com.example.testdb.Retrofit.RetrofitClientInstance;
import com.example.testdb.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private ActivityLoginBinding mainBinding;

    private AlertDialog dialog;

    private static String TAG = "여기 클릭하면";

    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;

    // 로그인 한 아이디 Toast
    String loginId;
    String id;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        // 스마트폰 키보드 자판. -> 동작하는지 재확인 필요.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // 아직은 확인 불가.
        getSupportActionBar().hide();

        // 자동로그인
        appConfig = new AppConfig(this);
        if(appConfig.isUserLogin()) {
            // 유저 로그인 아이디 -> Intent 로 넘기기.
            String name = appConfig.getNameOfUser();
            Intent intent = new Intent(login.this, MainActivity.class);
//             값 넘기기. -> 닉네임 값 띄우기 (로그인 화면 ) MainActivity.class
            intent.putExtra("name",name);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "로그인 한 아이디" + name, Toast.LENGTH_SHORT).show();
            finish();
        }

        // 아이디 찾기 화면 이동.
        mainBinding.btFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(login.this, findId.class));
            }
        });

        // 비밀번호 재설정 화면 이동.
        mainBinding.btFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(login.this, findPassword.class));
            }
        });


        // 로그인 하기 버튼 클릭시.
        mainBinding.btLoginSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
                mainBinding.showProgress.setVisibility(View.VISIBLE);
                // 로그인 한 아이디 Toast로 띄우기/
                loginId = mainBinding.etLoginId.getText().toString();
                Log.e(TAG, "onClick: 로그인 아이디 " + loginId );
            }
        });

        //개인정보처리방침 보여주기.
//        mainBinding.btPrivacy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(login.this, WebView.class));
//            }
//        });

        // 회원가입 화면 이동.
        mainBinding.btSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(login.this, signUp.class));
            }
        });
    }

    // Retrofit 라이브러리
    private void performLogin() {
        id = mainBinding.etLoginId.getText().toString();
        pw = mainBinding.etLoginPw.getText().toString();

//        saltHash = "dutyfreedom" + pw + "amondduty";
//        SHA516_Hash_InCode hash_inCode = new SHA516_Hash_InCode();
//        hashPw = hash_inCode.SHA516_Hash_InCode(saltHash);

        // 예외처리.
        // 아이디 혹은 비밀번호 칸이 비어있을 경우.
        if(id.equals("") || pw.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
            dialog = builder.setMessage("아이디와 비밀번호를 확인해주세요.").setPositiveButton("확인",null).create();
            dialog.show();
            return;
            // 업무선택으로 바로가기.
        }
        if (id.equals("az") && pw.equals("az")) {
//            Intent homeIntent = new Intent(login.this, home.class);
//            startActivity(homeIntent);
            return;
        }

        // http 통신.
        Call<MemberShip> call = RetrofitClientInstance.getRetrofitInstance()
                .create(GetDataService.class)
                .performUserLogin(id,pw);

        call.enqueue(new Callback<MemberShip>() {
            @Override
            public void onResponse(Call<MemberShip> call, Response<MemberShip> response) {
                if(response.code() == 200) {
                    if(response.body().getStatus().equals("ok")) {
                        if(response.body().getResultCode() == 1) {
                            String name = response.body().getNickName();
                            // 자동 로그인.
                            if(isRememberUserLogin) {
                                appConfig.updateUserLoginStatus(true);
                                appConfig.saveNameOfUser(name);
                            }
                            // main 화면 교체 후, 메인화면으로 이동 // 기존 home.class. 21.11.2.
                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.putExtra("name",name);
                            Toast.makeText(getApplicationContext(), "로그인 한 아이디" + name, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        } else {
                            displayUserInformation("로그인 실패...333");
                            // 패스워드 칸을 비워둠.
                            mainBinding.etLoginPw.setText("");
                        }
                    } else {
                        displayUserInformation("무엇인가 잘못되고 있어요..111");
                        mainBinding.etLoginPw.setText("");
                    }
                } else {
                    displayUserInformation("무엇인가 잘못되고 있어요..222");
                    mainBinding.etLoginPw.setText("");
                }
            }
            @Override
            public void onFailure(Call<MemberShip> call, Throwable t) {
                // 실패했을 경우.
                Toast.makeText(getApplicationContext(), "로그인 실패" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // 정보 표시
    private void displayUserInformation(String message) {
        Snackbar.make(mainBinding.constraintLogin, message, Snackbar.LENGTH_SHORT).show();
        mainBinding.showProgress.setVisibility(View.VISIBLE);
    }

    // CheckBox 자동로그인 체크
    public void checkBoxClicked(View view) {
        isRememberUserLogin = ((CheckBox)view).isChecked();
    }
}