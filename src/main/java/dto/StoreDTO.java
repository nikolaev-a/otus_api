
package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StoreDTO {

    private Boolean complete;

    private Long id;

    private Long petId;

    private Long quantity;

    private String shipDate;

    private String status;
}
