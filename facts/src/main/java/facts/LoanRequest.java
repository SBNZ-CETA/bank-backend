package facts;

import dtos.LoanRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="loan_requests_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private Double requestedAmount;
    private Integer rates;
    private WorkStatus workStatus;
    private String startingWorkDateIfEmployed;
    private String expirationWorkDateIfLimited;


    public LoanRequest(LoanRequestDto dto, User user) {
       this.user = user;
       this.requestedAmount = dto.getRequestedAmount();
       this.rates = dto.getRates();
       this.workStatus = dto.getWorkStatus();
       this.startingWorkDateIfEmployed = dto.getStartingWorkDateIfEmployed();
       this.expirationWorkDateIfLimited = dto.getExpirationWorkDateIfLimited();
    }
}
