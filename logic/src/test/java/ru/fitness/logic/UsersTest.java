package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IWuser;
import ru.fitness.dao.WuserManager;
import ru.fitness.dto.DUser;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersTest {
    private WuserManager wuserManager;

    @BeforeEach
    public void beforeEach() {
        wuserManager = Mockito.mock(WuserManager.class);
    }

    @Test
    public void getUsersTest() {
        IWuser user1 = Mockito.mock(IWuser.class);
        IWuser user2 = Mockito.mock(IWuser.class);
        when(user1.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(2);
        when(user1.getName()).thenReturn("user1");
        when(user2.getName()).thenReturn("user2");
        when(wuserManager.getAll()).thenReturn(Arrays.asList(user1, user2));
        assertThat(new UsersImpl(wuserManager).getUsers(),
                equalTo(Arrays.asList(new DUser(1, "user1"), new DUser(2, "user2"))));
        verify(wuserManager).getAll();
    }
}
