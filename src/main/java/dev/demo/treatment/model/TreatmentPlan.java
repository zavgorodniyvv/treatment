package dev.demo.treatment.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class TreatmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String patientId;
    private TreatmentAction action;
    private long startTime;
    private long endTime;
//      ┌───────────── second (0-59)
//      │ ┌───────────── minute (0 - 59)
//      │ │ ┌───────────── hour (0 - 23)
//      │ │ │ ┌───────────── day of the month (1 - 31)
//      │ │ │ │ ┌───────────── month (1 - 12)
//      │ │ │ │ │ ┌───────────── day of the week (0 - 7)
//      │ │ │ │ │ │
//      │ │ │ │ │ │
//      * * * * * *
    private String recurrencePattern;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public TreatmentAction getAction() {
        return action;
    }

    public void setAction(TreatmentAction action) {
        this.action = action;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(String recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentPlan that = (TreatmentPlan) o;
        return id == that.id && startTime == that.startTime && endTime == that.endTime && Objects.equals(patientId, that.patientId) && action == that.action && Objects.equals(recurrencePattern, that.recurrencePattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, action, startTime, endTime, recurrencePattern);
    }

    @Override
    public String toString() {
        return "TreatmentPlan{" +
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", action=" + action +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", recurrencePattern='" + recurrencePattern + '\'' +
                '}';
    }
}
