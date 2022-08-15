package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;

public enum TrafficLightingEvent implements IEvent{
    RESET,
    ACTIVATE,
    DEACTIVATE,
    NEXT;
}

