package com.dwhi219.c195.model;

/**
 * Standard bean for storing appointment type summary information. Used in the
 * Appointment Types by Month report.
 *
 * @author Daniel White
 */
public class AptTypeSummary {

    private String type;
    private int count;

    public AptTypeSummary() {
    }

    public AptTypeSummary(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
