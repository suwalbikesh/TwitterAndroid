package com.example.twitter.bill;

import com.example.twitter.api.UsersAPI;
import com.example.twitter.server_responses.SignUpResponse;
import com.example.twitter.url.Base_url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBill {
    boolean isSuccess = false;

    public boolean checkUser(String username, String password) {

        UsersAPI usersAPI = Base_url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> usersCall = usersAPI.checkUser(username, password);

        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Successful")) {

                Base_url.token += loginResponse.body().getUsertoken();

                isSuccess = true;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
