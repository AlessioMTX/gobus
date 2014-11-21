package com.dfba.gobus;

/**
 * Created by claudio on 21/11/14.
 */
public class Stop {
    String line;
    String distance_mt;
    String stop_address;
    String time;

    public Stop(String line, String distance_mt, String stop_address, String time) {
        this.line = line;
        this.distance_mt = distance_mt;
        this.stop_address = stop_address;
        this.time = time;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDistance_mt() {
        return distance_mt;
    }

    public void setDistance_mt(String distance_mt) {
        this.distance_mt = distance_mt;
    }

    public String getStop_address() {
        return stop_address;
    }

    public void setStop_address(String stop_address) {
        this.stop_address = stop_address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
