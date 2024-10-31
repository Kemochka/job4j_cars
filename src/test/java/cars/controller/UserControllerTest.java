package cars.controller;

import cars.model.User;
import cars.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    UserController userController;
    UserService userService;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenUserRegistrationAndRedirectIndex() {
        var user = new User(1, "ex@1.ya", "Ivan", "password", "timeZone");
        var userArgCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.create(userArgCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(user, model);
        var savedUser = userArgCaptor.getValue();
        assertThat(view).isEqualTo("redirect:/");
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Почта или пароль введены неверно");
        when(userService.create(any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var servlet = new MockHttpServletRequest();
        var view = userController.loginUser(new User(), model, servlet);

        var actualExceptionMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenUserLoginAndRedirectIndex() {
        var user = new User(1, "ex@1.ya", "Ivan", "password", "timeZone");
        when(userService.findByLoginAndPassword(any(), any())).thenReturn(Optional.of(user));
        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var view = userController.loginUser(user, model, request);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    public void whenRequestLoginPageThenGetLoginView() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenRequestLogoutThenGetIndexView() {
        var session = new MockHttpSession();
        var view = userController.logout(session);
        assertThat(view).isEqualTo("redirect:/users/login");
    }
}