package ru.fitness.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTypeRepoAdapterImpl implements EventTypeRepoAdapter {
    private final EventTypeRepository repo;

    public EventTypeRepoAdapterImpl(EventTypeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<IEventType> getNextEventType(long workoutId) {
        List<EventType> eventTypes = repo.getNextEventType(workoutId, PageRequest.of(0, 1, Sort.by("eventOrder").ascending()));
        return eventTypes.isEmpty() ? Optional.empty(): Optional.of(eventTypes.get(0));
    }
}
