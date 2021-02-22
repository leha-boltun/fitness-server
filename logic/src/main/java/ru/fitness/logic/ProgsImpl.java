package ru.fitness.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.fitness.dao.ProgManager;
import ru.fitness.dto.DProg;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
public class ProgsImpl implements Progs {
    private final ProgManager progManager;

    public ProgsImpl(ProgManager progManager) {
        this.progManager = progManager;
    }

    @Override
    public List<DProg> getProgs() {
        return progManager.getProgs().stream().map(p -> new DProg(p.getId(), p.getName())).collect(Collectors.toList());
    }
}
