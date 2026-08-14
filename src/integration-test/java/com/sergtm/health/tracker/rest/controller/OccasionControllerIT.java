package com.sergtm.health.tracker.rest.controller;

import com.sergtm.OccasionLevel;
import com.sergtm.entities.Occasion;
import com.sergtm.health.tracker.AbstractIntegrationTest;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.OccasionRepository;
import com.sergtm.health.tracker.persistence.repository.PersonRepository;
import com.sergtm.health.tracker.rest.request.OccasionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPerson;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OccasionControllerIT extends AbstractIntegrationTest {
    private static final Long OCCASION_ID = 1L;
    private static final String GET_ALL_OCCASIONS_URL = "/occasions";
    private static final String DELETE_OCCASION_URL = "/occasions/{occasionId}";
    private static final String CREATE_OCCASION_URL = "/occasions/{personId}";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private OccasionRepository occasionRepository;

    @Test
    void get_shouldReturnAllOccasions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_OCCASIONS_URL)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void put_shouldCreateOccasion() throws Exception {
        Person firstPerson = personRepository.save(createFirstPerson());

        OccasionRequest request = OccasionRequest.builder()
                .id(OCCASION_ID)
                .convulsion(false)
                .occasionLevel(OccasionLevel.LOW)
                .occasionDate(LocalDateTime.now())
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put(CREATE_OCCASION_URL, firstPerson.getId())
                        .queryParams(convertRequestToMultiValueMap(request))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void delete_shouldDeleteOccasion() throws Exception {
        Occasion firstOccasion = Occasion.builder().build();
        occasionRepository.save(firstOccasion);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(DELETE_OCCASION_URL, firstOccasion.getId())
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Occasion> occasionOpt = occasionRepository.findById(firstOccasion.getId());
        assertTrue(occasionOpt.isEmpty());
    }
}
