package org.jetbrains.assignment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
    NORTH("NORTH", "y", "+"),
    SOUTH("SOUTH", "y", "-"),
    WEST("WEST", "x", "-"),
    EAST("EAST", "x", "+");

    private final String name;
    private final String coordinate;
    private final String operation;
}
