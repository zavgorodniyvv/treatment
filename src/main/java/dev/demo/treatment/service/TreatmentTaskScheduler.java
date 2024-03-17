package dev.demo.treatment.service;

import dev.demo.treatment.dao.TreatmentPlanRepository;
import dev.demo.treatment.dao.TreatmentTaskRepository;
import dev.demo.treatment.model.TreatmentPlan;
import dev.demo.treatment.model.TreatmentTask;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentTaskScheduler {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TreatmentTaskScheduler.class);
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TreatmentTaskRepository treatmentTaskRepository;
    private final RecurrencePatternParser patternParser;

    @Value("${task.scheduled.interval}")
    private long interval;

    public TreatmentTaskScheduler(TreatmentPlanRepository treatmentPlanRepository,
                                  TreatmentTaskRepository treatmentTaskRepository,
                                  RecurrencePatternParser patternParser) {
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.patternParser = patternParser;
    }


    @Scheduled(fixedDelayString = "${task.scheduled.interval}", initialDelay = 2000L)
    public void generateTasksFromPlans() {
        logger.info("Generating tasks from treatment plans for {}", LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        List<TreatmentPlan> plans = treatmentPlanRepository.findByEndTimeIsNullOrEndTimeAfter(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        for (TreatmentPlan plan : plans) {
            Optional<LocalDateTime> startTimeOptional = patternParser.shouldGenerate(plan.getRecurrencePattern(), now, now.plus(interval, ChronoUnit.MILLIS));
            if (startTimeOptional.isPresent()) {
                TreatmentTask task = new TreatmentTask();
                task.setTreatmentAction(plan.getAction());
                task.setPatientId(plan.getPatientId());
                task.setStartTime(startTimeOptional.get().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                treatmentTaskRepository.save(task);
                logger.info("Task generated for patient {} at {}. Task: {}", plan.getPatientId(), startTimeOptional.get(), task);
            }
        }
    }
}
