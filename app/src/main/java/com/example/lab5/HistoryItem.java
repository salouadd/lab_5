package com.example.lab5;

public class HistoryItem {
    private String type;
    private String calculation;
    private String timestamp;

    public HistoryItem(String type, String calculation, String timestamp) {
        this.type = type;
        this.calculation = calculation;
        this.timestamp = timestamp;
    }

    public String getType() { return type; }
    public String getCalculation() { return calculation; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + type + ": " + calculation;
    }
}