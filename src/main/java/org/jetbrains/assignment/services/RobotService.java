package org.jetbrains.assignment.services;

import org.jetbrains.assignment.models.CoordinateRequest;
import org.jetbrains.assignment.models.CoordinateResponse;
import org.jetbrains.assignment.models.DirectionRequest;
import org.jetbrains.assignment.models.DirectionResponse;

import java.util.List;

public interface RobotService {
    List<CoordinateResponse> changeLocations(List<DirectionRequest> requests);

    List<DirectionResponse> moves(List<CoordinateRequest> requests);
}
