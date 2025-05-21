package org.qubership.integration.platform.designtime.catalog.rest.v1.dto.system.context;

import lombok.Data;
import org.qubership.integration.platform.catalog.model.dto.user.UserDTO;

@Data
public class ContextSystemResponseDTO {
    private String id;
    private String name;
    private String description;
    private Long createdWhen;
    private UserDTO createdBy;
    private Long modifiedWhen;
    private UserDTO modifiedBy;
}
