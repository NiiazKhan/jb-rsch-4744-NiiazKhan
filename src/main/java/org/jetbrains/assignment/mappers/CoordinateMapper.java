package org.jetbrains.assignment.mappers;


import org.jetbrains.assignment.constants.Direction;
import org.jetbrains.assignment.entities.CoordinateEntity;
import org.jetbrains.assignment.models.CoordinateRequest;
import org.jetbrains.assignment.models.CoordinateResponse;
import org.jetbrains.assignment.models.DirectionRequest;
import org.jetbrains.assignment.models.DirectionResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CoordinateMapper {

    private final AtomicLong locationX = new AtomicLong(0);
    private final AtomicLong locationY = new AtomicLong(0);
    private final AtomicLong moveX = new AtomicLong(0);
    private final AtomicLong moveY = new AtomicLong(0);

    public List<CoordinateEntity> toCoordinateEntites(List<DirectionRequest> directions) {
        List<CoordinateEntity> result = new ArrayList<>();
        result.add(new CoordinateEntity().setX(0L).setY(0L));
        result.addAll(directions.stream().map(this::toCoordinateEntity).toList());
        return result;
    }

    public List<CoordinateResponse> toCoordinateResponses(List<CoordinateEntity> coordinates) {
        return coordinates.stream()
                .map(coordinate -> new CoordinateResponse()
                        .setX(coordinate.getX())
                        .setY(coordinate.getY()))
                .toList();
    }

    public List<CoordinateEntity> toDirectionEntites(List<CoordinateRequest> coordinates) {
        return coordinates.stream()
                .map(coordinate -> new CoordinateEntity()
                        .setX(coordinate.getX())
                        .setY(coordinate.getY()))
                .toList();
    }

    public List<DirectionResponse> toDirectionResponses(List<CoordinateEntity> coordinates) {
        return coordinates.stream()
                .skip(1)
                .map(this::getDirectionResponse)
                .toList();
    }

    //  TODO not thread save
    private DirectionResponse getDirectionResponse(CoordinateEntity coordinate) {
        DirectionResponse directionResponse = new DirectionResponse();
        Long x1 = coordinate.getX();
        Long y1 = coordinate.getY();
        long actualX = moveX.get();
        long actualY = moveY.get();

        if (actualX == x1) {
            long resY = Math.abs(y1 - actualY);
            directionResponse.setSteps(resY);
            if (y1 > 0) {
                directionResponse.setDirection(Direction.NORTH);
            } else {
                directionResponse.setDirection(Direction.SOUTH);
            }
            moveY.set(y1);
        } else if (actualY == y1) {
            long resX = Math.abs(x1 - actualX);
            directionResponse.setSteps(resX);
            if (actualX > x1) {
                directionResponse.setDirection(Direction.WEST);
            } else {
                directionResponse.setDirection(Direction.EAST);
            }
            moveX.set(x1);
        }
        return directionResponse;
    }

    private CoordinateEntity toCoordinateEntity(DirectionRequest direction) {
        if ("x".equals(direction.getDirection().getCoordinate())) {
            if ("+".equals(direction.getDirection().getOperation())) {
                locationX.addAndGet(direction.getSteps());
            } else {
                locationX.addAndGet(-direction.getSteps());
            }
        } else {
            if ("+".equals(direction.getDirection().getOperation())) {
                locationY.addAndGet(direction.getSteps());
            } else {
                locationY.addAndGet(-direction.getSteps());
            }
        }
        return new CoordinateEntity()
                .setX(locationX.get())
                .setY(locationY.get());
    }

}
