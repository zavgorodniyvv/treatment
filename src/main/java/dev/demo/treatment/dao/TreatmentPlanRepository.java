package dev.demo.treatment.dao;

import dev.demo.treatment.model.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {
    @Query("select tp from TreatmentPlan tp where tp.endTime = 0 or tp.endTime > :endTime")
    List<TreatmentPlan> findByEndTimeIsNullOrEndTimeAfter(long endTime);
}
