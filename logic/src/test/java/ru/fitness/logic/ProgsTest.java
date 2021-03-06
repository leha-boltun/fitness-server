package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IProg;
import ru.fitness.dao.ProgManager;
import ru.fitness.dto.DProg;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProgsTest {
    private ProgManager progManager;

    @BeforeEach
    public void beforeEach() {
        progManager = Mockito.mock(ProgManager.class);
    }

    @Test
    public void getProgsTest() {
        IProg prog1 = Mockito.mock(IProg.class);
        IProg prog2 = Mockito.mock(IProg.class);
        when(prog1.getId()).thenReturn(1L);
        when(prog2.getId()).thenReturn(2L);
        when(prog1.getName()).thenReturn("prog1");
        when(prog2.getName()).thenReturn("prog2");
        when(progManager.getProgs()).thenReturn(Arrays.asList(prog1, prog2));
        assertThat(new ProgsImpl(progManager).getProgs(),
                equalTo(Arrays.asList(new DProg(1, "prog1"), new DProg(2, "prog2"))));
        verify(progManager).getProgs();
    }
}
