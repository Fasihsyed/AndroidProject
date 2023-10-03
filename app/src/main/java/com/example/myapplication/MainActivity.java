package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.WebApi;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.fragments.GroupFragment;
import com.example.myapplication.model.UserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ... Your existing code

        binding.buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebApi api = RetrofitClient.getInstance().getMyApi();
                UserDetail userobj = new UserDetail();
                userobj.userEmail = binding.edittextEmail.getText().toString();
                userobj.userpassword = binding.edittextPassword.getText().toString();
                api.login(userobj).enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if (response.isSuccessful()) {
                            UserDetail user = response.body();
                            if (user != null) {
                                // Assuming you have access to your GroupFragment instance


                                Intent i = new Intent(MainActivity.this, Dashboard.class);
                                i.putExtra("userId", user.getUserId()); // Pass the user ID as an extra
                                startActivity(i);
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid user details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
