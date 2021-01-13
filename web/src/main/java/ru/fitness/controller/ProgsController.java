package ru.fitness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fitness.dto.DProg;
import ru.fitness.logic.Progs;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class ProgsController {
    private final Progs progs;

    public ProgsController(Progs progs) {
        this.progs = progs;
    }

    @GetMapping("/progs")
    @Transactional
    public List<DProg> getProgs() {
        return progs.getProgs();
    }
}
