package com.shubhamkumarwinner.internsolution.moneyConvertor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shubhamkumarwinner.internsolution.R;
import com.shubhamkumarwinner.internsolution.data.network.MoneyConvertorApiStatus;
import com.shubhamkumarwinner.internsolution.databinding.MoneyConvertorFragmentBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MoneyConvertorFragment extends Fragment {

    private MoneyConvertorViewModel mViewModel;
    private MoneyConvertorFragmentBinding binding;
    int value;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MoneyConvertorFragmentBinding.inflate(getLayoutInflater(), container, false);
        mViewModel = new ViewModelProvider(this).get(MoneyConvertorViewModel.class);

        mViewModel.get_moneyConvertorStatus().observe(getViewLifecycleOwner(), new Observer<MoneyConvertorApiStatus>() {
            @Override
            public void onChanged(MoneyConvertorApiStatus moneyConvertorApiStatus) {
                if(moneyConvertorApiStatus == MoneyConvertorApiStatus.LOADING){
                    binding.noDataText.setVisibility(View.GONE);
                    binding.loadingBar.setVisibility(View.VISIBLE);
                    binding.data.setVisibility(View.GONE);
                }else if(moneyConvertorApiStatus == MoneyConvertorApiStatus.ERROR){
                    binding.loadingBar.setVisibility(View.GONE);
                    binding.data.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                }else {
                    binding.loadingBar.setVisibility(View.GONE);
                    binding.data.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.GONE);
                }
            }
        });
        binding.refreshTimeText.setText("last refresh "+getLastRefreshTime());
        binding.btnConvert.setOnClickListener(view -> {
            String valueString = binding.editTextValue.getText().toString();
            if(!valueString.equals("")){
                value = Integer.parseInt(valueString);
            }else {
                value = 1;
            }
            retrieveData(value);
        });

        binding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData(1);
                saveLastRefreshTime();
                binding.refreshTimeText.setText("last refresh "+getLastRefreshTime());
            }
        });
        retrieveData(1);
        return binding.getRoot();
    }

    private void retrieveData(int value){
        mViewModel.get_moneyConvertor().observe(getViewLifecycleOwner(), moneyConvertor -> {
            if(moneyConvertor.getRates() != null) {
                binding.chf.setText(String.valueOf(moneyConvertor.getRates().CHF * value));
                binding.sgd.setText(String.valueOf(moneyConvertor.getRates().SGD * value));
                binding.pln.setText(String.valueOf(moneyConvertor.getRates().PLN * value));
                binding.bgn.setText(String.valueOf(moneyConvertor.getRates().BGN * value));
                binding.tRY.setText(String.valueOf(moneyConvertor.getRates().TRY * value));
                binding.cny.setText(String.valueOf(moneyConvertor.getRates().CNY * value));
                binding.nok.setText(String.valueOf(moneyConvertor.getRates().NOK * value));
                binding.nzd.setText(String.valueOf(moneyConvertor.getRates().NZD * value));
                binding.zar.setText(String.valueOf(moneyConvertor.getRates().ZAR * value));
                binding.usd.setText(String.valueOf(moneyConvertor.getRates().USD * value));
                binding.mxn.setText(String.valueOf(moneyConvertor.getRates().MXN * value));
                binding.ils.setText(String.valueOf(moneyConvertor.getRates().ILS * value));
                binding.gbp.setText(String.valueOf(moneyConvertor.getRates().GBP * value));
                binding.krw.setText(String.valueOf(moneyConvertor.getRates().KRW * value));
                binding.myr.setText(String.valueOf(moneyConvertor.getRates().MYR * value));
            }else {
                binding.noDataText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveLastRefreshTime(){
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String date = formatter.format(System.currentTimeMillis());
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TIME", date);
        editor.apply();
    }

    private String getLastRefreshTime(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("TIME", "09/26/2020 12:24");
    }

}