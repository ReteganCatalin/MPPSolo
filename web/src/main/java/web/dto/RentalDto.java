package web.dto;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class RentalDto extends BaseDto {

    private Long clientID;
    private Long movieID;
    private int year;
    private int day;
    private int month;

}
