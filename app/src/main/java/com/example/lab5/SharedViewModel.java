package com.example.lab5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<String>> history = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getHistory() {
        return history;
    }

    public void addEntry(String entry) {
        List<String> currentHistory = history.getValue();
        if (currentHistory != null) {
            currentHistory.add(0, entry); // Add at the beginning
            history.setValue(currentHistory);
        }
    }

    public void clearHistory() {
        history.setValue(new ArrayList<>());
    }
}