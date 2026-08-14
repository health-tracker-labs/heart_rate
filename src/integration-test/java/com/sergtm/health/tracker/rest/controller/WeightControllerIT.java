package com.sergtm.health.tracker.rest.controller;

import com.sergtm.entities.Weight;
import com.sergtm.health.tracker.AbstractIntegrationTest;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.persistence.repository.WeightRepository;
import com.sergtm.health.tracker.rest.request.WeightRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPerson;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WeightControllerIT extends AbstractIntegrationTest {
    private static final Long WEIGHT_ID = 1L;
    private static final String DELETE_WEIGHT_URL = "/weights/{weightId}";
    private static final String CREATE_WEIGHT_URL = "/weights/{personId}";
    private static final String GET_ALL_WEIGHTS_URL = "/weights";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private WeightRepository weightRepository;

    @Test
    void get_shouldReturnAllWeights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_WEIGHTS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void put_shouldCreateWeight() throws Exception {
        Person firstPerson = personRepository.save(createFirstPerson());

        WeightRequest request = WeightRequest.builder()
                .id(WEIGHT_ID)
                .weight(BigDecimal.ONE)
                .date(LocalDate.now())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put(CREATE_WEIGHT_URL, firstPerson.getId())
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void delete_shouldDeleteWeight() throws Exception {
        Weight firstWeight = Weight.builder().build();
        weightRepository.save(firstWeight);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_WEIGHT_URL, firstWeight.getId())
                )
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Weight> weightOpt = weightRepository.findById(firstWeight.getId());
        assertTrue(weightOpt.isEmpty());
    }
}
