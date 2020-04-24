package web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class StatMovieDto implements Serializable {
    private Integer year;
    private List<String> titles;

}
