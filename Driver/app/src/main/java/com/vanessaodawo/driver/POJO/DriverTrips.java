package com.vanessaodawo.driver.POJO;

import java.sql.Time;

/**
 * Created by Vanessa on 02/05/2018.
 */

public class DriverTrips {

    Time dropOffTime;
    int dropOffLocation;

    public DriverTrips(Time dropOffTime, int dropOffLocation) {
        this.dropOffTime = dropOffTime;
        this.dropOffLocation = dropOffLocation;
    }

    public Time getDropOffTime() {
        return dropOffTime;
    }

    public void setDropOffTime(Time dropOffTime) {
        this.dropOffTime = dropOffTime;
    }

    public int getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(int dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }
}
