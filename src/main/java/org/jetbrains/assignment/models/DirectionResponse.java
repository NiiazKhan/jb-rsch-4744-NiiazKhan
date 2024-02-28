package org.jetbrains.assignment.models;


import lombok.Data;
import org.jetbrains.assignment.constants.Direction;

@Data
public class DirectionResponse {

    private Direction direction;
    private Long steps;

}
