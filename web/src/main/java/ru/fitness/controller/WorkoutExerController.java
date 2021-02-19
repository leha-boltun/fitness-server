package ru.fitness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DWSetsPrev;
import ru.fitness.dto.DWset;
import ru.fitness.logic.WorkoutExer;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class WorkoutExerController {
    private final WorkoutExer workoutExer;

    public WorkoutExerController(WorkoutExer workoutExer) {
        this.workoutExer = workoutExer;
    }

    @GetMapping("/workoutexer/{id}/main")
    @Transactional
    public List<DWset> getWsets(@PathVariable("id") long id) {
        workoutExer.setId(id);
        return workoutExer.getWsets();
    }

    @GetMapping("/workoutexer/{id}/previd")
    @Transactional
    public DWSetsPrev getWSetsPrev(@PathVariable("id") long id) {
        workoutExer.setId(id);
        return workoutExer.getWsetsAndPrevId();
    }
}
