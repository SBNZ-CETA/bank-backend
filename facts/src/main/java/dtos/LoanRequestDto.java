package dtos;

import lombok.Data;

@Data
public class LoanRequestDto {
    private enum WorkStatus {
        UNEMPLOYED,
        EMPLOYED,
        EMPLOYEDLIMITED
    }

    private Double requestedAmount;
    private Integer rates;
    private WorkStatus workStatus;
    private String startingWorkDateIfEmployed;
    private String expirationWorkDateIfLimited;
}
