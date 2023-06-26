package facts;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Column
    private double latitude;
    @Column
    private double longitude;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        System.out.println(6371.0 * c);
        return 6371.0 * c;      //EARTH_RADIUS_KM = 6371.0
    }
}
