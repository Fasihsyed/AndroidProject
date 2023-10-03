package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.api.WebApi;
import com.example.myapplication.databinding.ActivityDashboardBinding;
import com.example.myapplication.fragments.GroupFragment;
import com.example.myapplication.model.DataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        String userId = getIntent().getStringExtra("userId");
        if (userId != null) {
            // You have the userId, you can use it as needed
            Toast.makeText(this, "User ID: " + userId, Toast.LENGTH_SHORT).show();
        } else {
            // Handle the case where userId is not passed properly
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
        populateListView();
        setContentView(binding.getRoot());

        // Set an item click listener for the ListView
        binding.listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event here
                DataModel selectedDataModel = (DataModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(Dashboard.this, GroupDetailActivity.class);
                intent.putExtra("dataModel", selectedDataModel);
                startActivity(intent);
            }
        });

        // The rest of your code...
    }

    private void populateListView() {
        WebApi api = RetrofitClient.getInstance().getMyApi();
        api.getgroup().enqueue(new Callback<ArrayList<DataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DataModel>> call, Response<ArrayList<DataModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DataModel> userDetails = response.body();
                    ArrayAdapter<DataModel> adp = new ArrayAdapter<>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            userDetails);
                    binding.listViewUsers.setAdapter(adp);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DataModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}