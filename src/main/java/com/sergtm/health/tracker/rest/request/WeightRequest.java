package com.sergtm.health.tracker.rest.request;

import com.sergtm.entities.Weight;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeightRequest {
    @ApiModelProperty(hidden = true)
    private Long id;

    @DecimalMin("1")
    @DecimalMax("999.999")
    @ApiModelProperty(required = true)
    private BigDecimal weight;

    @ApiModelProperty(required = true, example = "yyyy-MM-dd")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate date;

    public WeightRequest(Weight weight) {
        this.id = weight.getId();
        this.date = weight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.weight = weight.getWeight();
    }
}
