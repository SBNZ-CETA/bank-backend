package dtos;

import facts.BankAccount;
import lombok.Data;
import java.util.Date;

@Data
public class BankAccountDto {
    private Long id;
    private Integer ccv;
    private Date expirationDate;
    private Double balance;
}
