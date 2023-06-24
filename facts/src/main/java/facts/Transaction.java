package facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private BankAccount senderAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    private BankAccount receiverAccount;
    @Column
    private Integer amount;
    @Column
    private boolean suspicious;
    @Column
    private LocalDateTime transactionTime;
    @Column
    private String location;
    @Column
    private boolean confirmed;
}
