package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.ProgRepoAdapter;
import ru.fitness.dto.DProg;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class ProgsImpl implements Progs {
    private final ProgRepoAdapter progRepo;

    public ProgsImpl(ProgRepoAdapter progRepo) {
        this.progRepo = progRepo;
    }

    @Override
    public List<DProg> getProgs() {
        return progRepo.getProgs().stream().map(p -> new DProg(p.getId(), p.getName())).collect(Collectors.toList());
    }
}
