package com.example.orderfooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.orderfooduser.POJO.CommonModel;
import com.example.orderfooduser.POJO.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class StartActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=3000;
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        Paper.init(this);

        String user=Paper.book().read(CommonModel.USER_KEY);
        String pass= Paper.book().read(CommonModel.PWD_KEY);
        if(user!=null && pass!=null){
            if(!user.isEmpty() && !pass.isEmpty()){
                login(user,pass);
            }
        }
        if(c==0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(StartActivity.this,
                            LoginActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.
                }
            }, SPLASH_SCREEN_TIME_OUT);
        }

    }

    private void login(final String user, final String pwd) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(user).exists()) {
                    UserModel userModel = snapshot.child(user).getValue(UserModel.class);
                    if (userModel.getUserPasssword().equals(pwd)) {

                        Intent i = new Intent(StartActivity.this, MainActivity.class);
                        CommonModel.currentUser = userModel;
                        startActivity(i);
                        finish();
                        c++;
                    } else {

                        Toast.makeText(StartActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StartActivity.this, "User does not exists!!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}