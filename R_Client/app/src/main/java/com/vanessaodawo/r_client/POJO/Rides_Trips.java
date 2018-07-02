package com.vanessaodawo.r_client.POJO;

public class Rides_Trips {
    String from, time;
    int cost;

    public Rides_Trips(String from, String time, int cost) {
        this.from = from;
        this.time = time;
        this.cost = cost;
    }

    public Rides_Trips() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
