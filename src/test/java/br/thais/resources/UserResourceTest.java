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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    private static final Integer ID = 1;
    private static final Integer INDEX = 0;
    private static final String NAME = "Thais";
    private static final String EMAIL = "thais@email.com";
    private static final String PASSWORD = "123";

    private User user = new User();
    private UserDTO userDTO = new UserDTO();

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

    @Test
    void whenFindAllList() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateWithSuccess() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateWithSuccess() {
        when(service.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);
        assertThat(response)
                .isNotNull();
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getClass())
                .isEqualTo(ResponseEntity.class);
        assertThat(response.getBody().getClass())
                .isEqualTo(UserDTO.class);
        assertThat(response.getBody().getId())
                .isEqualTo(ID);
        assertThat(response.getBody().getName())
                .isEqualTo(NAME);
        assertThat(response.getBody().getEmail())
                .isEqualTo(EMAIL);
    }

    @Test
    void whenDeleteWithSuccess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);
        assertThat(response)
                .isNotNull();
        assertThat(response.getClass())
                .isEqualTo(ResponseEntity.class);
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        verify(service, times(1)).delete(anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}