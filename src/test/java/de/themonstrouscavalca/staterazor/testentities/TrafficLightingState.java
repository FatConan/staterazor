package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.state.interfaces.IState;

public enum TrafficLightingState implements IState{
    GO,
    WARN,
    STOP,
    READY,
    OFF;
}
