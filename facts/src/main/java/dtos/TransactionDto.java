package dtos;

import facts.BankAccount;
import lombok.Data;

@Data
public class TransactionDto {
    private long senderAccountId;
    private long receiverAccountId;
    private Integer amount;
    private String location;
}
