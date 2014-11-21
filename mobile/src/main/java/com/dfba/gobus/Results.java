package com.dfba.gobus;

import java.util.ArrayList;

/**
 * Created by claudio on 21/11/14.
 */
public class Results {

    ArrayList<Stop> stops;

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public Results(ArrayList<Stop> stops) {
        this.stops = stops;
    }
}
