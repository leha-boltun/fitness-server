package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.WuserManager;
import ru.fitness.dto.DUser;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class UsersImpl implements Users {
    private final WuserManager userManager;

    public UsersImpl(WuserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public List<DUser> getUsers() {
        return userManager.getAll().stream()
                .map( (user) -> new DUser(user.getId(), user.getName()))
                .collect(Collectors.toList());
    }
}
