package com.sergtm.client;

import com.sergtm.controllers.rest.response.PersonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class PersonApiClientTest {
    private static final String URI = "/persons";

    private static final Long PERSON_ID = 8L;
    private static final String PERSON_NAME = "Tymofieiev S, Oleksii";
    private static final String COUNTRY = "Ukraine";

    @Mock
    private WebClient healthTrackerWebClient;
    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PersonApiClient testedInstance;

    @Test
    void shouldReturnPersonsWhenServerReturns200() {
        final ClientResponse response = ClientResponse
                .create(OK)
                .header("Content-Type", "application/json")
                .body("[{\"id\":8, \"name\": \"Tymofieiev S, Oleksii\", \"country\": \"Ukraine\"}]")
                .build();
        mockClientResponse(response);

        Collection<PersonResponse> responses = testedInstance.getPersons();
        PersonResponse personResponse = responses.stream().findFirst().get();

        assertEquals(PERSON_ID, personResponse.getId());
        assertEquals(PERSON_NAME, personResponse.getName());
        assertEquals(COUNTRY, personResponse.getCountry());
    }

    @Test
    void shouldThrowExceptionWhenServerReturns500() {
        final ClientResponse response = ClientResponse
                .create(INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
        mockClientResponse(response);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            testedInstance.getPersons();
        });
        assertEquals(INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
    }

    private void mockClientResponse(ClientResponse response) {
        doReturn(requestHeadersUriSpec).when(healthTrackerWebClient).get();
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(URI);
        doAnswer(invocation -> {
            Function<ClientResponse, Flux<PersonResponse>> func = invocation.getArgument(0);
            return func.apply(response);
        }).when(requestHeadersUriSpec).exchangeToFlux(any());
    }
}
