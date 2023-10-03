package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.WebApi;
import com.example.myapplication.databinding.FragmentGroupBinding;
import com.example.myapplication.model.DataModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GroupFragment extends Fragment {
    private FragmentGroupBinding binding;
    private WebApi myApi; // Your Retrofit API service, initialize it properly

    // Store the user ID once the user logs in
    private String userId;
    public static GroupFragment newInstance(String userId) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            userId = getArguments().getString("userId");
        }

        binding = FragmentGroupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize your Retrofit API service here
        Retrofit retrofit = RetrofitClient.getRetrofit();
        myApi = retrofit.create(WebApi.class);
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = dateFormat.format(Calendar.getInstance().getTime());

                // Check if a user is authenticated (custom authentication)
                if (userId != null) {
                    // Create a data model instance
                    DataModel dataModel = new DataModel();
                    dataModel.groupName = binding.editTextGroupName.getText().toString();
                    dataModel.groupDescription = binding.editTextGroupDescription.getText().toString();
                    dataModel.groupDate = currentDate;
                    dataModel.userId = userId; // Use the user's ID

                    // Use your Retrofit API service to create a new group
                    if (myApi != null) {
                        Call<Void> call = myApi.createNewGroup(dataModel);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // Show a success message using Toast
                                    Toast.makeText(getContext(), "Group created successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Show an error message using Toast
                                    Toast.makeText(getContext(), "Failed to create group", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), "Failed to create group: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                // Handle failure
                            }
                        });
                    }
                } else {
                    // User is not authenticated, handle this case
                    Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
                    // You might want to redirect the user to the login screen or perform other actions.
                }
            }
        });

        return view;
    }

    // Method to set the user ID after login (custom authentication)
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
