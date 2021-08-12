package com.shubhamkumarwinner.internsolution.moneyConvertor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shubhamkumarwinner.internsolution.data.network.MoneyConvertorApiStatus;
import com.shubhamkumarwinner.internsolution.databinding.MoneyConvertorFragmentBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MoneyConvertorFragment extends Fragment {

    int value;
    private MoneyConvertorViewModel mViewModel;
    private MoneyConvertorFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MoneyConvertorFragmentBinding.inflate(getLayoutInflater(), container, false);
        mViewModel = new ViewModelProvider(this).get(MoneyConvertorViewModel.class);

        mViewModel.get_moneyConvertorStatus().observe(getViewLifecycleOwner(), new Observer<MoneyConvertorApiStatus>() {
            @Override
            public void onChanged(MoneyConvertorApiStatus moneyConvertorApiStatus) {
                if (moneyConvertorApiStatus == MoneyConvertorApiStatus.LOADING) {
                    binding.noDataText.setVisibility(View.GONE);
                    binding.loadingBar.setVisibility(View.VISIBLE);
                    binding.data.setVisibility(View.GONE);
                } else if (moneyConvertorApiStatus == MoneyConvertorApiStatus.ERROR) {
                    binding.loadingBar.setVisibility(View.GONE);
                    binding.data.setVisibility(View.GONE);
                    binding.noDataText.setVisibility(View.VISIBLE);
                } else {
                    binding.loadingBar.setVisibility(View.GONE);
                    binding.data.setVisibility(View.VISIBLE);
                    binding.noDataText.setVisibility(View.GONE);
                    saveLastRefreshTime();
                    retrieveData(1);
                    binding.refreshTimeText.setText("last refresh " + getLastRefreshTime());
                }
            }
        });
        binding.btnConvert.setOnClickListener(view -> {
            String valueString = binding.editTextValue.getText().toString();
            if (!valueString.equals("")) {
                value = Integer.parseInt(valueString);
            } else {
                value = 1;
            }
            retrieveData(value);
            binding.editTextValue.onEditorAction(EditorInfo.IME_ACTION_DONE);
        });

        binding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editTextValue.setText("");
                binding.editTextValue.onEditorAction(EditorInfo.IME_ACTION_DONE);
                mViewModel.getMoneyRates();
                binding.loadingBar.setVisibility(View.VISIBLE);
                retrieveData(1);
            }
        });
        binding.refreshTimeText.setText("last refresh " + getLastRefreshTime());
        return binding.getRoot();
    }

    private void retrieveData(int value) {
        mViewModel.get_rates().observe(getViewLifecycleOwner(), rates -> {
            if (rates != null) {
                binding.chf.setText(String.valueOf(rates.getCHF() * value));
                binding.sgd.setText(String.valueOf(rates.getSGD() * value));
                binding.pln.setText(String.valueOf(rates.getPLN() * value));
                binding.bgn.setText(String.valueOf(rates.getBGN() * value));
                binding.tRY.setText(String.valueOf(rates.getTRY() * value));
                binding.cny.setText(String.valueOf(rates.getCNY() * value));
                binding.nok.setText(String.valueOf(rates.getNOK() * value));
                binding.nzd.setText(String.valueOf(rates.getNZD() * value));
                binding.zar.setText(String.valueOf(rates.getZAR() * value));
                binding.usd.setText(String.valueOf(rates.getUSD() * value));
                binding.mxn.setText(String.valueOf(rates.getMXN() * value));
                binding.ils.setText(String.valueOf(rates.getILS() * value));
                binding.gbp.setText(String.valueOf(rates.getGBP() * value));
                binding.krw.setText(String.valueOf(rates.getKRW() * value));
                binding.myr.setText(String.valueOf(rates.getMYR() * value));
                binding.noDataText.setVisibility(View.GONE);
                binding.data.setVisibility(View.VISIBLE);
                binding.loadingBar.setVisibility(View.GONE);
            } else {
                binding.data.setVisibility(View.GONE);
                binding.noDataText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveLastRefreshTime() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String date = formatter.format(System.currentTimeMillis());
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TIME", date);
        editor.apply();
    }

    private String getLastRefreshTime() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("TIME", "09/26/2020 12:24");
    }

}