package web.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieDto extends BaseDto {
    private String title;
    private  String genre;
    private int yearOfRelease;
    private String director;
    private String mainStar;
}