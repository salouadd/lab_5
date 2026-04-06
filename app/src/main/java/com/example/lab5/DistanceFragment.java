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

public class DistanceFragment extends Fragment {

    RadioGroup rgDist;
    RadioButton rbKmToMiles, rbMilesToKm, rbKmToM, rbMtoKm;
    EditText etDistInput;
    Button btnConvertDist, btnClearDist, btnCopyDist;
    TextView tvDistResult;

    private String lastResult = "";
    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distance, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        rgDist = view.findViewById(R.id.rgDist);
        rbKmToMiles = view.findViewById(R.id.rbKmToMiles);
        rbMilesToKm = view.findViewById(R.id.rbMilesToKm);
        rbKmToM = view.findViewById(R.id.rbKmToM);
        rbMtoKm = view.findViewById(R.id.rbMtoKm);
        etDistInput = view.findViewById(R.id.etDistInput);
        btnConvertDist = view.findViewById(R.id.btnConvertDist);
        btnClearDist = view.findViewById(R.id.btnClearDist);
        tvDistResult = view.findViewById(R.id.tvDistResult);
        btnCopyDist = view.findViewById(R.id.btnCopyDist);

        rgDist.setOnCheckedChangeListener((group, checkedId) -> resetResult());
        etDistInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { resetResult(); }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnClearDist.setOnClickListener(v -> {
            etDistInput.setText("");
            resetResult();
        });

        btnCopyDist.setOnClickListener(v -> {
            if (TextUtils.isEmpty(lastResult)) {
                Toast.makeText(getContext(), "Rien à copier", Toast.LENGTH_SHORT).show();
                return;
            }
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Résultat Conversion", lastResult);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copié !", Toast.LENGTH_SHORT).show();
        });

        btnConvertDist.setOnClickListener(v -> {
            String input = etDistInput.getText().toString();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(getContext(), "Entrer une valeur", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double val = Double.parseDouble(input);
                double result;
                String formula = "";

                if (rbKmToMiles.isChecked()) {
                    result = val * 0.6214;
                    lastResult = String.format("%.2f Miles", result);
                    formula = val + " Km → " + lastResult;
                } else if (rbMilesToKm.isChecked()) {
                    result = val / 0.6214;
                    lastResult = String.format("%.2f Km", result);
                    formula = val + " Miles → " + lastResult;
                } else if (rbKmToM.isChecked()) {
                    result = val * 1000;
                    lastResult = String.format("%.2f m", result);
                    formula = val + " Km → " + lastResult;
                } else {
                    result = val / 1000;
                    lastResult = String.format("%.2f Km", result);
                    formula = val + " m → " + lastResult;
                }

                tvDistResult.setText("Résultat : " + lastResult);

                String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                viewModel.addEntry("[" + time + "] Dist: " + formula);

            } catch (NumberFormatException e) {
                tvDistResult.setText("Invalide");
            }
        });

        return view;
    }

    private void resetResult() {
        tvDistResult.setText("Résultat : -");
        lastResult = "";
    }
}