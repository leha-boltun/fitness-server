package ru.fitness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DUserMain;
import ru.fitness.dto.DWorkout;
import ru.fitness.logic.User;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class UserController {
    private final User user;

    public UserController(User user) {
        this.user = user;
    }

    @GetMapping("/user/{userId}/workouts")
    @Transactional
    public List<DWorkout> getWorkouts(@PathVariable("userId") int userId) {
        user.setUserId(userId);
        return user.getWorkouts();
    }

    @PostMapping("/user/{userId}/newWorkout")
    @Transactional
    public DWorkout newWorkout(@PathVariable("userId") int userId) {
        user.setUserId(userId);
        return user.createWorkout();
    }

    @GetMapping("/user/{userId}/main")
    @Transactional
    public DUserMain getMain(@PathVariable("userId") int userId) {
        user.setUserId(userId);
        return user.getMain();
    }
}
