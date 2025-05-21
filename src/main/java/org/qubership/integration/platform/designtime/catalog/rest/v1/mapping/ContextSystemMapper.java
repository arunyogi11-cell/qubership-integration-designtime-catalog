package org.qubership.integration.platform.designtime.catalog.rest.v1.mapping;


import java.util.List;
import org.mapstruct.*;
import org.qubership.integration.platform.catalog.persistence.configs.entity.context.ContextSystem;
import org.qubership.integration.platform.catalog.util.MapperUtils;
import org.qubership.integration.platform.designtime.catalog.rest.v1.dto.system.context.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        uses = {
                MapperUtils.class
        }
)
public interface ContextSystemMapper {

    List<ContextSystemResponseDTO> toContextSystemResponsesDTOs(List<ContextSystem> contextSystems);


    ContextSystemResponseDTO toContextSystemResponseDTO(ContextSystem contextSystem);

    default ContextSystem toContextSystem(ContextSystemRequestDTO requestedContextSystem) {
        return ContextSystem.builder()
                .id(requestedContextSystem.getId())
                .name(requestedContextSystem.getName())
                .description(requestedContextSystem.getDescription())
                .build();
    }

    ContextSystem update(@MappingTarget ContextSystem contextSystem, ContextSystemUpdateRequestDTO request);
}
