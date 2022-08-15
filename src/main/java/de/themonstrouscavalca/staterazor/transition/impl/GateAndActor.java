package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IActor;
import de.themonstrouscavalca.staterazor.transition.interfaces.IGate;

public class GateAndActor<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>{
    private final IGate<M,S,E,X> gate;
    private final IActor<M,S,E,X> actor;

    public GateAndActor(IGate<M,S,E,X> gate, IActor<M,S,E,X> actor){
        this.gate = gate;
        this.actor = actor;
    }

    public IGate<M,S,E,X> gate(){
        return gate;
    }
    public IActor<M,S,E,X> getActor(){
        return actor;
    }
}
