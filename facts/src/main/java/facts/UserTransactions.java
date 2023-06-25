package facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTransactions {
    private long userId;
    private List<Transaction> transactionList;

    public UserTransactions(long userId){
        this.userId = userId;
    }
}
