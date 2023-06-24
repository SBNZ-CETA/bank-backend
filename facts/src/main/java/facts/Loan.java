package facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Position;

import javax.persistence.*;

@Entity
@Table(name="loans_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Position(0)
    private Long id;
    @Column
    @Position(1)
    private Integer minimumPaymentDeadline;
    @Column
    @Position(2)
    private Integer maximumPaymentDeadline;
    @Column
    @Position(3)
    private Double loanAmount;
}
