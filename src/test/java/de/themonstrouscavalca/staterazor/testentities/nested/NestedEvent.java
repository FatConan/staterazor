package de.themonstrouscavalca.staterazor.testentities.nested;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;

public enum NestedEvent implements IEvent{
    UP,
    DOWN,
    LEFT,
    RIGHT,
    ROTATE_CLOCKWISE,
    ROTATE_ANTICLOCKWISE,
    PEN_DOWN,
    PEN_UP,
    STOP
}
