package facts;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserTransactions {
    private long userId;
    private List<Transaction> transactionList;

    public UserTransactions(long userId){
        this.userId = userId;
    }
}
