package com.shubhamkumarwinner.internsolution.moneyConvertor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shubhamkumarwinner.internsolution.data.MoneyConvertor;
import com.shubhamkumarwinner.internsolution.data.Rates;
import com.shubhamkumarwinner.internsolution.data.network.MoneyConvertorApiStatus;
import com.shubhamkumarwinner.internsolution.data.network.MoneyConvertorApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoneyConvertorViewModel extends ViewModel {
    private MutableLiveData<MoneyConvertorApiStatus> _moneyConvertorStatus = new MutableLiveData<>();

    public LiveData<MoneyConvertorApiStatus> get_moneyConvertorStatus() {
        return _moneyConvertorStatus;
    }

    private MutableLiveData<Rates> _rates = new MutableLiveData<>();

    public LiveData<Rates> get_rates() {
        return _rates;
    }

    public MoneyConvertorViewModel(){
        getMoneyRates();
    }

    public void getMoneyRates() {
        new MoneyConvertorApiService().moneyConvertorApi.getRates().enqueue(new Callback<MoneyConvertor>() {
            @Override
            public void onResponse(Call<MoneyConvertor> call, Response<MoneyConvertor> response) {
                _moneyConvertorStatus.setValue(MoneyConvertorApiStatus.LOADING);
                if (response.body()!=null){
                    _rates.setValue(response.body().getRates());
                    _moneyConvertorStatus.setValue(MoneyConvertorApiStatus.DONE);
                }
            }

            @Override
            public void onFailure(Call<MoneyConvertor> call, Throwable t) {
                _moneyConvertorStatus.setValue(MoneyConvertorApiStatus.ERROR);
                _rates.setValue(null);
            }
        });
    }

}