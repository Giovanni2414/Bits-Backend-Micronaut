package edu.co.icesi;

import edu.co.icesi.model.User;
import edu.co.icesi.repository.UserRepository;
import edu.co.icesi.service.UserService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.mockito.internal.verification.Times;

import java.util.UUID;

import static org.mockito.Mockito.*;

@MicronautTest
@Requires(property = "mockito.test.enabled", defaultValue = StringUtils.FALSE, value = StringUtils.TRUE)
public class UserServiceTest {

    @Inject
    private UserService userService;

    @Inject
    private final UserRepository userRepository = mock(UserRepository.class);

    private User setupScenario1() {
        User user = new User();
        user.setFirstname("Giovanni");
        user.setLastname("Mosquera");
        user.setUserId(UUID.randomUUID());
        user.setPassword("password");
        user.setOrganizationName("Icesi");
        user.setEmail("Giovanni2414g@gmail.com");
        return user;
    }

    @Test
    void createUser() {
        User user = setupScenario1();
        when(userRepository.save(any())).thenReturn(user);
        User createdUser = userService.createUser(user);
        verify(userRepository.save(any()), times(1));
        Assertions.assertEquals(user.getEmail(), createdUser.getEmail(), "Emails do not match");
    }

}
