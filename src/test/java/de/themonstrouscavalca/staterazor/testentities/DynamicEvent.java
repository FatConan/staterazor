package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;

public enum DynamicEvent implements IEvent{
    ADD,
    REMOVE,
    PREVIOUS,
    NEXT,
    START,
    END,
    PROGRESS,
    COMPLETE,
    NO_OP
}
