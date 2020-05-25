package web.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class SortDto implements Serializable {
    private List<SortObjectDTO> sort;

}