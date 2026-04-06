package com.example.lab5;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TempFragment extends Fragment {

    RadioGroup rgTemp;
    RadioButton rbCtoF, rbFtoC, rbCtoK, rbKtoC;
    EditText etTempInput;
    Button btnConvertTemp, btnClearTemp, btnCopyTemp;
    TextView tvTempResult;

    private String lastResult = "";
    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_temp, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        rgTemp = view.findViewById(R.id.rgTemp);
        rbCtoF = view.findViewById(R.id.rbCtoF);
        rbFtoC = view.findViewById(R.id.rbFtoC);
        rbCtoK = view.findViewById(R.id.rbCtoK);
        rbKtoC = view.findViewById(R.id.rbKtoC);
        etTempInput = view.findViewById(R.id.etTempInput);
        btnConvertTemp = view.findViewById(R.id.btnConvertTemp);
        btnClearTemp = view.findViewById(R.id.btnClearTemp);
        tvTempResult = view.findViewById(R.id.tvTempResult);
        btnCopyTemp = view.findViewById(R.id.btnCopyTemp);

        rgTemp.setOnCheckedChangeListener((group, checkedId) -> resetResult());
        etTempInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { resetResult(); }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnClearTemp.setOnClickListener(v -> {
            etTempInput.setText("");
            resetResult();
        });

        btnCopyTemp.setOnClickListener(v -> {
            if (TextUtils.isEmpty(lastResult)) {
                Toast.makeText(getContext(), "Rien à copier", Toast.LENGTH_SHORT).show();
                return;
            }
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Résultat Conversion", lastResult);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copié !", Toast.LENGTH_SHORT).show();
        });

        btnConvertTemp.setOnClickListener(v -> {
            String input = etTempInput.getText().toString();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(getContext(), "Entrer une valeur", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double val = Double.parseDouble(input);
                double result;
                String formula = "";

                if (rbCtoF.isChecked()) {
                    result = (1.8 * val) + 32;
                    lastResult = String.format("%.2f °F", result);
                    formula = val + " °C → " + lastResult;
                } else if (rbFtoC.isChecked()) {
                    result = (val - 32) / 1.8;
                    lastResult = String.format("%.2f °C", result);
                    formula = val + " °F → " + lastResult;
                } else if (rbCtoK.isChecked()) {
                    result = val + 273.15;
                    lastResult = String.format("%.2f K", result);
                    formula = val + " °C → " + lastResult;
                } else {
                    result = val - 273.15;
                    lastResult = String.format("%.2f °C", result);
                    formula = val + " K → " + lastResult;
                }

                tvTempResult.setText("Résultat : " + lastResult);
                
                String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                viewModel.addEntry("[" + time + "] Temp: " + formula);

            } catch (NumberFormatException e) {
                tvTempResult.setText("Invalide");
            }
        });

        return view;
    }

    private void resetResult() {
        tvTempResult.setText("Résultat : -");
        lastResult = "";
    }
}