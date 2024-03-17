package dev.demo.treatment.dao;

import dev.demo.treatment.model.TreatmentTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentTaskRepository extends JpaRepository<TreatmentTask, Long> {
}
