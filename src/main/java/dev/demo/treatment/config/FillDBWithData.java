package dev.demo.treatment.config;

import dev.demo.treatment.dao.TreatmentPlanRepository;
import dev.demo.treatment.model.TreatmentAction;
import dev.demo.treatment.model.TreatmentPlan;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
@Profile("dev")
public class FillDBWithData {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FillDBWithData.class);
    private final TreatmentPlanRepository treatmentPlanRepository;

    public FillDBWithData(TreatmentPlanRepository treatmentPlanRepository) {
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    @PostConstruct
    public void init() {
        logger.info("Filling the database with sample data in development profile.");
        TreatmentPlan plan = new TreatmentPlan();
        plan.setId(1L);
        plan.setPatientId("1");
        plan.setAction(TreatmentAction.ACTION_A);
        plan.setStartTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setEndTime(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setRecurrencePattern("0 0 8,9,10,11,12,13,14,15,16,17,18 * * *");
        treatmentPlanRepository.save(plan);

        plan.setId(2L);
        plan.setPatientId("2");
        plan.setAction(TreatmentAction.ACTION_B);
        plan.setStartTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setEndTime(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setRecurrencePattern("0 0 " + LocalDateTime.now().getHour() + " " + LocalDateTime.now().plusMinutes(1).getMinute() + " * *");
        treatmentPlanRepository.save(plan);

        plan.setId(3L);
        plan.setPatientId("32");
        plan.setAction(TreatmentAction.ACTION_A);
        plan.setStartTime(LocalDateTime.of(2024, 3, 10, 12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setEndTime(LocalDateTime.of(2024, 3, 15, 12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setRecurrencePattern("0 0 14 * * *");
        treatmentPlanRepository.save(plan);

        plan = new TreatmentPlan();
        plan.setId(4L);
        plan.setPatientId("44");
        plan.setAction(TreatmentAction.ACTION_B);
        plan.setStartTime(LocalDateTime.of(2024, 3, 10, 12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        plan.setRecurrencePattern("0 0 14 * * *");
        treatmentPlanRepository.save(plan);
        logger.info("Added {} records to Treatment_Plan table", treatmentPlanRepository.findAll().size());
    }

}
