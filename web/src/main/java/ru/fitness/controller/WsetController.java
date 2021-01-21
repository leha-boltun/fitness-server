package ru.fitness.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DWset;
import ru.fitness.logic.Wset;

import javax.transaction.Transactional;

@RestController
public class WsetController {
    private final Wset wset;

    public WsetController(Wset wset) {
        this.wset = wset;
    }

    @PostMapping("/wset/{workoutExerId}/create")
    @Transactional
    public void createWset(@PathVariable("workoutExerId") long workoutExerId, @RequestBody DWset data) {
        wset.createWset(workoutExerId, data);
    }

    @PutMapping("/wset/edit")
    @Transactional
    public void editWset(@RequestBody DWset data) {
        wset.editWset(data);
    }
}
