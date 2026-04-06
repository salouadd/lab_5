package com.example.lab5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private SharedViewModel viewModel;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView lvHistory = view.findViewById(R.id.lvHistory);
        Button btnClearHistory = view.findViewById(R.id.btnClearHistory);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        lvHistory.setAdapter(adapter);

        viewModel.getHistory().observe(getViewLifecycleOwner(), items -> {
            adapter.clear();
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
        });

        btnClearHistory.setOnClickListener(v -> viewModel.clearHistory());

        return view;
    }
}