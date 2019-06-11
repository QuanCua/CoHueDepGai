package com.quancua.cohuedepgai;

public class SensorObject {
    public int moist;
    public float temp;

    public SensorObject() {
    }

    public SensorObject(int moist, float temp) {
        this.moist = moist;
        this.temp = temp;
    }

    public int getMoist() {
        return moist;
    }

    public float getTemp() {
        return temp;
    }
}
