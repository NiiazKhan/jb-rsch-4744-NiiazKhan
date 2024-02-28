package org.jetbrains.assignment.controllers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.assignment.models.CoordinateRequest;
import org.jetbrains.assignment.models.CoordinateResponse;
import org.jetbrains.assignment.models.DirectionRequest;
import org.jetbrains.assignment.models.DirectionResponse;
import org.jetbrains.assignment.services.RobotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RobotController {

    public static final String LOCATIONS_POST = "/locations";
    public static final String MOVES_POST = "/moves";

    private final RobotService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(LOCATIONS_POST)
    public List<CoordinateResponse> changeLocations(@RequestBody List<DirectionRequest> requests) {
        return service.changeLocations(requests);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(MOVES_POST)
    public List<DirectionResponse> moves(@RequestBody List<CoordinateRequest> requests) {
        return service.moves(requests);
    }


}
