package dev.demo.treatment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
public class TreatmentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TreatmentAction treatmentAction;
    private String patientId;
    private long startTime;
    private TaskStatus status = TaskStatus.ACTIVE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentAction getTreatmentAction() {
        return treatmentAction;
    }

    public void setTreatmentAction(TreatmentAction treatmentAction) {
        this.treatmentAction = treatmentAction;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentTask that = (TreatmentTask) o;
        return startTime == that.startTime && Objects.equals(id, that.id) && treatmentAction == that.treatmentAction && Objects.equals(patientId, that.patientId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, treatmentAction, patientId, startTime, status);
    }

    @Override
    public String toString() {
        return "TreatmentTask{" +
                "id=" + id +
                ", treatmentAction=" + treatmentAction +
                ", patientId='" + patientId + '\'' +
                ", startTime=" + LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault()) +
                ", status=" + status +
                '}';
    }
}
