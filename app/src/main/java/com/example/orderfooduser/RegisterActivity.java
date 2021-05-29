package com.example.orderfooduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfooduser.POJO.CommonModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.orderfooduser.POJO.UserModel;

public class RegisterActivity extends AppCompatActivity {

    EditText user_name,user_email,user_mobile,user_password,user_cpassword;
    Button register;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);
        user_mobile=findViewById(R.id.user_mobile);
        user_password=findViewById(R.id.user_password);
        user_cpassword=findViewById(R.id.user_cpassword);
        register=findViewById(R.id.Register);


        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= user_email.getText().toString().trim();
                final String pass= user_password.getText().toString().trim();
                final String cpass= user_cpassword.getText().toString().trim();
                final String name= user_name.getText().toString().trim();
                final String mobile= user_mobile.getText().toString().trim();


                if(TextUtils.isEmpty(name) &&TextUtils.isEmpty(mobile)&&TextUtils.isEmpty(email)&&TextUtils.isEmpty(pass)&&TextUtils.isEmpty(cpass)){

                    user_name.setError("Please Enter Name");
                    user_mobile.setError("Please Enter Mobile Number");
                    user_email.setError("Please Enter Email");

                    user_password.setError("Please Enter Password");
                    user_cpassword.setError("Please Confirm Password");
                    return;
                }

                if(TextUtils.isEmpty(name)) {
                    user_name.setError("Please Enter Name");
                    return;
                }
                if(TextUtils.isEmpty(mobile)) {
                    user_mobile.setError("Please Enter Mobile Number");
                    return;
                }
                if(mobile.length()!=10) {
                    user_mobile.setError("Please Enter Valid Mobile Number");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    user_email.setError("Please Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    user_password.setError("Please Enter Password");
                    return;
                }
                if(TextUtils.isEmpty(cpass)){
                    user_cpassword.setError("Please Confirm Password");
                    return;
                }
                if(pass.length()<6){
                    user_password.setError("Password length should be 6");
                    return;
                }
                if(!cpass.equals(pass)){
                    user_cpassword.setError("Password not matched");
                    return;
                }

                final ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Please Waiting...");
                progressDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.child(mobile).exists()){
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Phone number already exixts!!!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            progressDialog.dismiss();
                            UserModel userModel = new UserModel(name, email, cpass);
                            table_user.child(mobile).setValue(userModel);
                            Toast.makeText(RegisterActivity.this, "Register successfully!!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}