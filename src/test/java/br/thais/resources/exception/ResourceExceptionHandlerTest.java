package br.thais.resources.exception;

import br.thais.service.exception.ObjectionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObjectionNotFoundException() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(
                new ObjectionNotFoundException("Objeto não encontrado"),
                new MockHttpServletRequest()
        );
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getClass())
                .isEqualTo(ResponseEntity.class);
        assertThat(response.getBody().getClass())
                .isEqualTo(StandardError.class);
        assertThat(response.getBody().getError())
                .isEqualTo("Objeto não encontrado");
        assertThat(response.getBody().getStatus())
                .isEqualTo(404);
        assertThat(response.getBody().getPath())
                .isNotEqualTo("/user/2");
        assertThat(response.getBody().getTimestamp())
                .isNotEqualTo(LocalDateTime.now());
    }

    @Test
    void testDataIntegrityViolationException() {

    }

}