package com.shubhamkumarwinner.internsolution.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MoneyConvertorApiService {
    private final String  BASE_URL = "https://lufickdev.bitbucket.io/api/latest/";

    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    public MoneyConvertorApi moneyConvertorApi = retrofit.create(MoneyConvertorApi.class);
}
