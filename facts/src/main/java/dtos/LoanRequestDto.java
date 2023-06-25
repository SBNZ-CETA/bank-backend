package dtos;

import facts.WorkStatus;
import lombok.Data;

@Data
public class LoanRequestDto {
    private Double requestedAmount;
    private Integer rates;
    private WorkStatus workStatus;
    private String startingWorkDateIfEmployed;
    private String expirationWorkDateIfLimited;
}
