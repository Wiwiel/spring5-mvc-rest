package wiwiel.training.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomerDTO {
    @Schema(description = "This is the first name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstname;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastname;

    @JsonProperty("customer_url")
    private String customerUrl;
}
