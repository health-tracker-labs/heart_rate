package com.sergtm.health.tracker.service.impl;

import com.sergtm.health.tracker.exception.PatientNotFoundException;
import com.sergtm.health.tracker.persistence.entity.Patient;
import com.sergtm.health.tracker.persistence.entity.Person;
import com.sergtm.health.tracker.persistence.repository.PatientRepository;
import com.sergtm.health.tracker.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatient;
import static com.sergtm.health.tracker.testsupport.entry.PatientEntryFixture.createPatientBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createFirstPersonBuilder;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createSecondPerson;
import static com.sergtm.health.tracker.testsupport.entry.PersonEntryFixture.createThirdPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    private static final String CAN_NOT_FIND_PATIENT_BY_PATIENT_ID_MSG = "Can't find patient by patient id = %s";

    private static final Long FIRST_PERSON_ID = 1L;
    private static final Long FIRST_PATIENT_ID = 2L;

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private IPersonService personService;
    @Captor
    private ArgumentCaptor<Patient> patientEntityCaptor;

    @InjectMocks
    private PatientService testedInstance;

    @Test
    void getPatients_shouldReturnEmptyCollection_whenNoPatientsWereFound() {
        doReturn(List.of())
                .when(patientRepository)
                .findAll();

        Collection<Patient> actual = testedInstance.getPatients();
        assertThat(actual).isEmpty();
    }

    @Test
    void getPatients_shouldReturnPatients_whenAnyWereFound() {
        List<Patient> expected = List.of(
                createPatient(createFirstPerson()),
                createPatient(createSecondPerson()),
                createPatient(createThirdPerson()));

        doReturn(expected)
                .when(patientRepository)
                .findAll();

        Collection<Patient> actual = testedInstance.getPatients();
        assertThat(actual)
                .hasSize(3)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void createPatient_shouldThrowException_whenPersonWasNotFound() {
        doThrow(new PatientNotFoundException(String.format(
                CAN_NOT_FIND_PATIENT_BY_PATIENT_ID_MSG, FIRST_PATIENT_ID)
        ))
                .when(personService)
                .findByIdOrThrowException(FIRST_PATIENT_ID);
        Throwable exception = assertThrows(PatientNotFoundException.class,
                () -> testedInstance.createPatient(FIRST_PATIENT_ID));

        assertEquals(String.format(CAN_NOT_FIND_PATIENT_BY_PATIENT_ID_MSG, FIRST_PATIENT_ID),
                exception.getMessage());

        verify(personService).findByIdOrThrowException(FIRST_PATIENT_ID);
        verifyNoInteractions(patientRepository);
    }

    @Test
    void createPatient_shouldReturnPatient_whenPersonWasFound() {
        Person person = createFirstPersonBuilder()
                .id(FIRST_PERSON_ID)
                .build();

        doReturn(person)
                .when(personService)
                .findByIdOrThrowException(FIRST_PATIENT_ID);
        doAnswer(invocation -> invocation.getArgument(0))
                .when(patientRepository)
                .save(any(Patient.class));

        Patient actual = testedInstance.createPatient(FIRST_PATIENT_ID);

        verify(personService).findByIdOrThrowException(FIRST_PATIENT_ID);
        verify(patientRepository).save(patientEntityCaptor.capture());

        Patient captured = patientEntityCaptor.getValue();

        assertNull(actual.getId());
        assertThat(actual.getPerson())
                .usingRecursiveComparison()
                .isEqualTo(captured.getPerson());
    }

    @Test
    void getPatientByIdOrThrowException_shouldReturnPatient_whenPatientWasFound() {
        Patient patient = createPatientBuilder()
                .id(FIRST_PATIENT_ID)
                .person(createFirstPerson())
                .build();
        doReturn(Optional.of(patient))
                .when(patientRepository)
                .findById(FIRST_PATIENT_ID);

        Patient actual = testedInstance.getPatientByIdOrThrowException(FIRST_PATIENT_ID);

        assertThat(actual.getPerson())
                .usingRecursiveComparison()
                .isEqualTo(patient.getPerson());
        verify(patientRepository).findById(FIRST_PATIENT_ID);
    }

    @Test
    void getPatientByIdOrThrowException_shouldThrowException_whenPatientWasNotFound() {
        doReturn(Optional.empty())
                .when(patientRepository)
                .findById(FIRST_PATIENT_ID);

        Throwable exception = assertThrows(PatientNotFoundException.class,
                () -> testedInstance.getPatientByIdOrThrowException(FIRST_PATIENT_ID));

        assertEquals(String.format(CAN_NOT_FIND_PATIENT_BY_PATIENT_ID_MSG, FIRST_PATIENT_ID),
                exception.getMessage());
    }
}
