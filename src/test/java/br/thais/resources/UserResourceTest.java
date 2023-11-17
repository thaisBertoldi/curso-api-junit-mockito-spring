package br.thais.resources;

import br.thais.domain.User;
import br.thais.domain.dto.UserDTO;
import br.thais.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    private static final Integer ID = 1;
    private static final Integer INDEX = 0;
    private static final String NAME = "Thais";
    private static final String EMAIL = "thais@email.com";
    private static final String PASSWORD = "123";

    private User user;
    private UserDTO userDTO;

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdWithSuccess() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertThat(ResponseEntity.class)
                .isNotNull()
                .isEqualTo(response.getClass());
        assertThat(UserDTO.class)
                .isNotNull()
                .isEqualTo(response.getBody().getClass());
        assertThat(response.getBody().getId())
                .isEqualTo(ID);
        assertThat(response.getBody().getName())
                .isEqualTo(NAME);
        assertThat(response.getBody().getEmail())
                .isEqualTo(EMAIL);
        assertThat(response.getBody().getPassword())
                .isEqualTo(PASSWORD);
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}