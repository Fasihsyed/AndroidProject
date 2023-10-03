package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.WebApi;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.model.UserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String userName=binding.etFullname.getText().toString();
                    String email=binding.etEmail.getText().toString();
                    String password=binding.etPassword.getText().toString();

//                    DBManager mgr=new DBManager( SignUpActivity.this);
//                    mgr.saveUser(uname,passwrd);
                    WebApi api= RetrofitClient.getInstance().getMyApi();
                    UserDetail userobj=new UserDetail();
                    userobj.userName=userName;
                    userobj.userEmail=email;
                    userobj.userpassword=password;
                    api.signup(userobj).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(Signup.this,
                                        "Data Saved", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Signup.this,Dashboard.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Signup.this, "Not Saved",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Signup.this, "not Saved"+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }

        });
    }catch (Exception e)
            {
                Toast.makeText(Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

//                Intent i=new Intent(Signup.this,Signin.class);
//                startActivity(i);
        }
    });

}
}