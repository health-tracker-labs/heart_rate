package com.sergtm.health.tracker.rest.request;

import com.sergtm.entities.Weight;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeightRequest {
    @Schema(hidden = true)
    private Long id;

    @DecimalMin("1")
    @DecimalMax("999.999")
    @Schema(requiredMode = REQUIRED)
    private BigDecimal weight;

    @Schema(requiredMode = REQUIRED, example = "yyyy-MM-dd")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate date;

    public WeightRequest(Weight weight) {
        this.id = weight.getId();
        this.date = weight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.weight = weight.getWeight();
    }
}
