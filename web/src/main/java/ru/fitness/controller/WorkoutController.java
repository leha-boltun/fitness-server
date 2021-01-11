package ru.fitness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkoutMain;
import ru.fitness.logic.Workout;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class WorkoutController {
    private final Workout workout;

    public WorkoutController(Workout workout) {
        this.workout = workout;
    }

    @GetMapping("/workout/{id}/timestamps")
    @Transactional
    public List<DTimeStampMain> getTimestamps(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.getTimeStamps();
    }

    @GetMapping("/workout/{id}/main")
    @Transactional
    public DWorkoutMain getMain(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.getMain();
    }

    @GetMapping("/workout/{id}/next")
    @Transactional
    public DNextEvent getNextEventName(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.getNextEventName();
    }

    @PostMapping("/workout/{id}/next")
    @Transactional
    public DNextEvent processNextEvent(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.processNextEvent();
    }
}
