package com.example.assignment1;

import java.util.Date;

/**
 * Medicine class
 */
public class Medicine {
    /**
     * Maximum medicine name length
     */
    public static final int MAX_LENGTH_NAME = 40;

    /**
     * Enum for dose unit options
     */
    public enum DoseUnit {
        MG,
        MCG,
        DROP
    }

    /**
     * Name of the medicine
     */
    private String name;

    /**
     * Date to start taking medicine
     */
    private Date startDate;

    /**
     * Daily frequency the medicine is taken
     */
    private int dailyFrequency;

    /**
     * Dose of the medicine
     */
    private int dose;

    /**
     * Dose unit
     */
    private DoseUnit doseUnit;

    /**
     * Constructor for medicine
     * @param name The name of the medicine (must not exceed MAX_LENGTH_NAME)
     * @param startDate The date to start taking the medicine
     * @param dailyFrequency The number of doses of medicine taken per day
     * @param dose The amount of medicine taken per dose
     * @param doseUnit The unit of the dose
     */
    public Medicine(String name, Date startDate, int dailyFrequency, int dose, DoseUnit doseUnit) {
        // Check name is less than max length
        if (name.length() > MAX_LENGTH_NAME) {
            throw new IllegalArgumentException("Medicine name is over character limit");
        }

        // Check daily frequency is positive
        if (dailyFrequency < 1) {
            throw new IllegalArgumentException("Daily frequency must be positive");
        }

        // Check dose is positive
        if (dose < 1) {
            throw new IllegalArgumentException("Dose must be positive");
        }

        // Set medicine values
        this.name = name;
        this.startDate = startDate;
        this.dailyFrequency = dailyFrequency;
        this.dose = dose;
        this.doseUnit = doseUnit;
    }

    /**
     * Getter method for name
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for start date
     * @return The start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter method for start date
     * @param startDate The new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter method for daily frequency
     * @return The daily frequency
     */
    public int getDailyFrequency() {
        return dailyFrequency;
    }

    /**
     * Setter method for daily frequency
     * @param dailyFrequency The new daily frequency
     */
    public void setDailyFrequency(int dailyFrequency) {
        this.dailyFrequency = dailyFrequency;
    }

    /**
     * Getter method for dose
     * @return The dose
     */
    public int getDose() {
        return dose;
    }

    /**
     * Setter method for dose
     * @param dose The new dose
     */
    public void setDose(int dose) {
        this.dose = dose;
    }

    /**
     * Getter method for dose unit
     * @return The dose unit
     */
    public DoseUnit getDoseUnit() {
        return doseUnit;
    }

    /**
     * Setter method for dose unit
     * @param doseUnit The new dose unit
     */
    public void setDoseUnit(DoseUnit doseUnit) {
        this.doseUnit = doseUnit;
    }
}
