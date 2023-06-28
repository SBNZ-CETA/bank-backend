package dtos;

import facts.BankAccount;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {
    private long senderAccountId;
    private long receiverAccountId;
    private Integer amount;
    private String location;
    private Integer ccv;
    private String ownerName;
    private Date expirationDate;
    private boolean isPayingFromBookstore;
}
