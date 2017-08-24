package com.sergtm.form;

public class AddHeartRateForm {
    private int lowerPressure;
    private int upperPressure;
    private long personId;

    public int getLowerPressure() {
        return lowerPressure;
    }
    public void setLowerPressure(int lowerPressure) {
        this.lowerPressure = lowerPressure;
    }

    public int getUpperPressure() {
        return upperPressure;
    }
    public void setUpperPressure(int upperPressure) {
        this.upperPressure = upperPressure;
    }

    public long getPersonId() {
        return personId;
    }
    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
