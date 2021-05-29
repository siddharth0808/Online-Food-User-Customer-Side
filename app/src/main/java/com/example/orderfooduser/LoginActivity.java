package com.example.orderfooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.orderfooduser.POJO.CommonModel;
import com.example.orderfooduser.POJO.UserModel;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    EditText Phone,Password;
    Button login_btn;
    TextView register_btn,forgot_btn;
    private ProgressDialog progressDialog;

    CheckBox checkBox;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            Phone = findViewById(R.id.Phone);
            Password = findViewById(R.id.Password);
            login_btn = findViewById(R.id.login_btn);
            forgot_btn = findViewById(R.id.forgot_btn);
            checkBox= findViewById(R.id.checkBox);
            Paper.init(this);
            progressDialog = new ProgressDialog(LoginActivity.this);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("User");


            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String phone = Phone.getText().toString().trim();
                    final String password = Password.getText().toString().trim();
                    if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(password)) {
                        Phone.setError("Please Enter Email");
                        Password.setError("Please Enter Password");
                        return;
                    }
                    if (TextUtils.isEmpty(phone)) {
                        Phone.setError("Please Enter Email");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Password.setError("Please Enter Password");
                        return;
                    }


                    if(checkBox.isChecked()){
                        Paper.book().write(CommonModel.USER_KEY,phone);
                        Paper.book().write(CommonModel.PWD_KEY,password);
                    }
                    progressDialog.setTitle("Loging...");
                    progressDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(phone).exists()) {
                                progressDialog.dismiss();
                                UserModel userModel = snapshot.child(phone).getValue(UserModel.class);
                                userModel.setUserMobile(phone);
                                if (userModel.getUserPasssword().equals(password)) {
                                    Toast.makeText(LoginActivity.this, "Login Successfully!!!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    CommonModel.currentUser = userModel;
                                    startActivity(i);
                                    finish();
                                } else {

                                    Password.setError("Invalid phone number/password");
                                }
                            } else {
                                Phone.setError("User does not exists");
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

            register_btn = findViewById(R.id.register_btn);
            register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(i);

                }
            });
            forgot_btn = findViewById(R.id.forgot_btn);
            forgot_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = Phone.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        Phone.setError("Please Enter Email");
                        return;
                    }
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            LoginActivity.this);

// Setting Dialog Title
                    alertDialog2.setTitle("Forgot Password ?");
                    alertDialog2.setMessage("" + email);

// Setting Dialog Message


// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("SEND",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog


                                }
                            });
                    alertDialog2.show();

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }
}