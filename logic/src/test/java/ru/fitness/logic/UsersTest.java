package ru.fitness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fitness.dao.IWUser;
import ru.fitness.dao.WUserRepoAdapter;
import ru.fitness.dto.DUser;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsersTest {
    private WUserRepoAdapter userRepo;

    @BeforeEach
    public void uploadFiles() {
        userRepo = Mockito.mock(WUserRepoAdapter.class);
    }

    @Test
    public void getUsersTest() {
        IWUser user1 = Mockito.mock(IWUser.class);
        IWUser user2 = Mockito.mock(IWUser.class);
        when(user1.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(2);
        when(user1.getName()).thenReturn("user1");
        when(user2.getName()).thenReturn("user2");
        when(userRepo.getAll()).thenReturn(Arrays.asList(user1, user2));
        assertThat(new UsersImpl(userRepo).getUsers(),
                equalTo(Arrays.asList(new DUser(1, "user1"), new DUser(2, "user2"))));
        verify(userRepo).getAll();
    }
}
