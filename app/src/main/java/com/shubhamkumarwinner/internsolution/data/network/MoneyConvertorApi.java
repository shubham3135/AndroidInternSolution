package com.shubhamkumarwinner.internsolution.data.network;

import com.shubhamkumarwinner.internsolution.data.MoneyConvertor;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoneyConvertorApi {
    @GET("/api/latest")
    Call<MoneyConvertor> getRates();
}
