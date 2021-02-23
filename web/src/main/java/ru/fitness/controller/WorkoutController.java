package ru.fitness.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DExer;
import ru.fitness.dto.DNextEvent;
import ru.fitness.dto.DTimeStampMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.dto.DWorkoutMain;
import ru.fitness.logic.Workout;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalTime;
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

    @GetMapping("/workout/maxUndoSeconds")
    public int getMaxUndoSeconds() {
        return workout.getMaxUndoSeconds();
    }

    @DeleteMapping("/workout/{id}/undo")
    @Transactional
    public DNextEvent undoEvent(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.undoEvent();
    }

    @PostMapping("/workout/{id}/nextSetWeight")
    @Transactional
    public DNextEvent processNextEventSetWeight(@PathVariable("id") long id, @RequestBody BigDecimal weight) {
        workout.setWorkoutId(id);
        workout.setWeight(weight);
        return workout.processNextEvent();
    }

    @PostMapping("/workout/{userId}/{progId}/{prevId}")
    @Transactional
    public DWorkout create(@PathVariable("userId") int userId, @PathVariable("progId") long progId,
                           @PathVariable("prevId") long prevProgId) {
        return workout.createWorkout(userId, progId, prevProgId);
    }

    @GetMapping("/workout/{id}/exers")
    @Transactional
    public List<DExer> getExers(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.getExers();
    }

    @GetMapping("/workout/{id}/total_time")
    @Transactional
    public LocalTime getTotalTime(@PathVariable("id") long id) {
        workout.setWorkoutId(id);
        return workout.getTotalTime().orElse(null);
    }

    @PatchMapping("/workout/{id}/weight")
    @Transactional
    public void editWeight(@PathVariable("id") long id, @RequestBody BigDecimal weight) {
        workout.setWorkoutId(id);
        workout.setWeight(weight);
    }
}
