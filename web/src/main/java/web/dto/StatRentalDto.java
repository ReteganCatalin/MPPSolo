package web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class StatRentalDto implements Serializable {
    public Integer ClientLeastAge;
    public Integer YearOfRelease;
}
