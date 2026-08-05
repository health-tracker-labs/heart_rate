package com.sergtm.health.tracker.rest.handler;

import com.sergtm.health.tracker.exception.NotFoundException;
import com.sergtm.health.tracker.exception.PatientNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class GlobalRestExceptionHandlerTest {
    private static final String PATIENT_NOT_FOUND_EXCEPTION_MSG = "Can't find patient";

    private GlobalRestExceptionHandler testedInstance = new GlobalRestExceptionHandler();

    @Test
    void handleNotFoundException_shouldReturnProblemDetail_whenHandlingNotFoundException() {
        ProblemDetail expectedProductDetail = ProblemDetail
                .forStatusAndDetail(NOT_FOUND, PATIENT_NOT_FOUND_EXCEPTION_MSG);

        NotFoundException ex = new PatientNotFoundException(PATIENT_NOT_FOUND_EXCEPTION_MSG);
        ProblemDetail productDetail = testedInstance.handleNotFoundException(ex);

        assertThat(productDetail)
                .usingRecursiveComparison()
                .isEqualTo(expectedProductDetail);
    }
}
