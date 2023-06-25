package facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeolocationResponse {
    private Double latitude;
    private Double longitude;
}
