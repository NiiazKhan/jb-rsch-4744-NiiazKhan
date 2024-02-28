package org.jetbrains.assignment.services.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.assignment.entities.CoordinateEntity;
import org.jetbrains.assignment.mappers.CoordinateMapper;
import org.jetbrains.assignment.models.CoordinateRequest;
import org.jetbrains.assignment.models.CoordinateResponse;
import org.jetbrains.assignment.models.DirectionRequest;
import org.jetbrains.assignment.models.DirectionResponse;
import org.jetbrains.assignment.repositories.RobotRepository;
import org.jetbrains.assignment.services.RobotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final RobotRepository repository;
    private final CoordinateMapper mapper;

    @Override
    public List<CoordinateResponse> changeLocations(List<DirectionRequest> requests) {
        List<CoordinateEntity> coordinates = mapper.toCoordinateEntites(requests);
        List<CoordinateEntity> saved = repository.saveAll(coordinates);
        return mapper.toCoordinateResponses(saved);
    }

    @Override
    public List<DirectionResponse> moves(List<CoordinateRequest> requests) {
        List<CoordinateEntity> entities =  mapper.toDirectionEntites(requests);
        List<CoordinateEntity> saved = repository.saveAll(entities);

        return mapper.toDirectionResponses(saved);
    }
}
