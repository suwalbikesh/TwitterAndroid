package com.example.twitter.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitter.R;
import com.example.twitter.adpters.PostAdapter;
import com.example.twitter.api.UsersAPI;
import com.example.twitter.models.Posts;
import com.example.twitter.url.Base_url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.RecyclePosts);

        UsersAPI usersAPI = Base_url.getInstance().create(UsersAPI.class);

        Call<List<Posts>> postCall = usersAPI.getAllPost();

        postCall.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Posts> postsList = response.body();
                PostAdapter postAdapter = new PostAdapter(getContext(),postsList);
                recyclerView.setAdapter(postAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error msg ", t.getLocalizedMessage());

            }
        });
        return root;
    }
}