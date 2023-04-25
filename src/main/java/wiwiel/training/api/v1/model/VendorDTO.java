package wiwiel.training.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VendorDTO {
    @Schema(description = "Vendor name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Link to vendor", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
