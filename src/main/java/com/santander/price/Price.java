package com.santander.price;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Price {
    private final long id;
    private final String instrument;
    private final double bid;
    private final double ask;
    private final LocalDateTime timestamp;

    private static final double commission = 0.001;

    public Price(long id, String instrument, double bid, double ask, LocalDateTime timestamp) {
        this.id = id;
        this.instrument = instrument;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getInstrument() {
        return instrument;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
        return id + ", " + instrument + ", " + bid + ", " + ask + ", " + formatter.format(timestamp);
    }
}
