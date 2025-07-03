// dto/ReservationStatusUpdateDTO.java
package edu.esoft.com.dto;

import edu.esoft.com.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationStatusUpdateDTO {

    @NotNull
    private ReservationStatus newStatus;   // CONFIRMED | NO_SHOW | COMPLETE
}
