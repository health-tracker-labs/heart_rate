package com.sergtm.health.tracker.rest.request;

import com.sergtm.OccasionLevel;
import com.sergtm.entities.Occasion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OccasionRequest {
    @Schema(hidden = true)
    private Long id;

    @Schema(requiredMode = REQUIRED)
    private OccasionLevel occasionLevel;

    @Schema(requiredMode = REQUIRED)
    private boolean convulsion;

    @Schema(requiredMode = REQUIRED, example = "yyyy-MM-ddTHH:mm:ss")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime occasionDate;

    public OccasionRequest(Occasion occasion) {
        this.id = occasion.getId();
        this.occasionLevel = occasion.getOccasionLevel();
        this.convulsion = occasion.isConvulsion();
        this.occasionDate = occasion.getOccasionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
