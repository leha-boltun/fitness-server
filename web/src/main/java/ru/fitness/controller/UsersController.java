package ru.fitness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DUser;
import ru.fitness.logic.Users;

import java.util.List;

@RestController
public class UsersController {
    private final Users users;

    public UsersController(Users users) {
        this.users = users;
    }

    @GetMapping("/users")
    public List<DUser> getUsers() {
        return users.getUsers();
    }
}
