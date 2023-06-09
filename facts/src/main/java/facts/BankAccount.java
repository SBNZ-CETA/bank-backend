package facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bank_account_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column
    private Integer ccv;
    @Column
    private Date expirationDate;
    @Column
    private Double balance;
    @Column
    private Double expectedIncome;

    public BankAccount(User user, Integer ccv, Date expirationDate, Double balance, Double income) {
        this.user = user;
        this.ccv = ccv;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.expectedIncome = income;
    }

}
