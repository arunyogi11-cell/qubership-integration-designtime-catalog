package org.qubership.integration.platform.designtime.catalog.service;

import lombok.extern.slf4j.Slf4j;
import org.qubership.integration.platform.catalog.persistence.configs.entity.actionlog.LogOperation;
import org.qubership.integration.platform.catalog.persistence.configs.entity.context.ContextSystem;
import org.qubership.integration.platform.catalog.persistence.configs.repository.context.ContextSystemRepository;
import org.qubership.integration.platform.catalog.service.AbstractContextSystemService;
import org.qubership.integration.platform.catalog.service.ActionsLogService;
import org.qubership.integration.platform.designtime.catalog.rest.v1.dto.system.SystemSearchRequestDTO;
import org.qubership.integration.platform.designtime.catalog.rest.v1.mapping.ContextSystemMapper;
import org.qubership.integration.platform.designtime.catalog.service.filter.SystemFilterSpecificationBuilder;
import org.qubership.integration.platform.designtime.catalog.rest.v1.dto.FilterRequestDTO;
import org.qubership.integration.platform.designtime.catalog.rest.v1.dto.system.context.ContextSystemRequestDTO;
import org.qubership.integration.platform.designtime.catalog.rest.v1.dto.system.context.ContextSystemUpdateRequestDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
    public class ContextSystemService extends AbstractContextSystemService {

    private final SystemFilterSpecificationBuilder systemFilterSpecificationBuilder;
    private final ContextSystemMapper contextSystemMapper;

    public ContextSystemService(ContextSystemRepository contextSystemRepository,
                                ContextSystemMapper contextSystemMapper,
                                ActionsLogService actionLogger,
                                SystemFilterSpecificationBuilder systemFilterSpecificationBuilder) {
        super(
                contextSystemRepository,
                actionLogger
        );
        this.systemFilterSpecificationBuilder = systemFilterSpecificationBuilder;
        this.contextSystemMapper = contextSystemMapper;
    }


    public ContextSystem create(ContextSystemRequestDTO requestedContextSystem) {
        requestedContextSystem.setId(UUID.randomUUID().toString());
        ContextSystem createdSystem = contextSystemMapper.toContextSystem(requestedContextSystem);
        return enrichAndSaveDatabaseSystem(createdSystem, false);
    }

    public void deleteById(String systemId) {
        ContextSystem contextSystem = findById(systemId);
        contextSystemRepository.delete(contextSystem);
        logContextSystemAction(contextSystem, LogOperation.DELETE);

    }

    public ContextSystem update(ContextSystemUpdateRequestDTO requestContextSystem, String systemId) {
        ContextSystem contextSystem = findById(systemId);
        contextSystem = contextSystemMapper.update(contextSystem, requestContextSystem);
        contextSystem = save(contextSystem);
        logContextSystemAction(contextSystem, LogOperation.UPDATE);
        return contextSystem;
    }

    public List<ContextSystem> searchContextSystems(SystemSearchRequestDTO systemSearchRequestDT) {
        return contextSystemRepository.searchForContextSystems(systemSearchRequestDT.getSearchCondition());
    }


    @Transactional
    public List<ContextSystem> findByFilterRequest(List<FilterRequestDTO> filters) {
        Specification<ContextSystem> specification = systemFilterSpecificationBuilder.buildContextFilter(filters);

        return contextSystemRepository.findAll(specification);
    }
}
