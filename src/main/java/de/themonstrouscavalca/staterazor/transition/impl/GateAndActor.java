package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IActor;
import de.themonstrouscavalca.staterazor.transition.interfaces.IGate;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

public class GateAndActor<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>{
    private final IGate<M,T,C,S,E,X> gate;
    private final IActor<M,T,C,S,E,X> actor;

    public GateAndActor(IGate<M,T,C,S,E,X> gate, IActor<M,T,C,S,E,X> actor){
        this.gate = gate;
        this.actor = actor;
    }

    public IGate<M,T,C,S,E,X> gate(){
        if(gate == null){
            return (t, ic) -> true;
        }
        return gate;
    }

    public IActor<M,T,C,S,E,X> getActor(){
        return actor;
    }
}
