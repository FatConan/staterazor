package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IActor;
import de.themonstrouscavalca.staterazor.transition.interfaces.IGate;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

/**
 * The GateAndActor links together two parts of the transition together: the Gate that determines whether criteria
 * are met to proceed with a transition, and the Actor that performs an action as a result of the transition occurring. This
 * pairing are key to any transition.
 *
 * @param <M> The state machine class
 * @param <T> The transition class
 * @param <C> The transition scope class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The event context class
 */
public class GateAndActor<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>{
    private final IGate<M,T,C,S,E,X> gate;
    private final IActor<M,T,C,S,E,X> actor;

    /**
     * Create a pairing of the gate and actor for a given transition
     * @param gate The Gate that checks whether the transition may proceed
     * @param actor The Actor that performs actions should the transition occur
     */
    public GateAndActor(IGate<M,T,C,S,E,X> gate, IActor<M,T,C,S,E,X> actor){
        this.gate = gate;
        this.actor = actor;
    }

    /**
     * Gate getter
     * @return the Gate
     */
    public IGate<M,T,C,S,E,X> gate(){
        if(gate == null){
            return (t, ic) -> true;
        }
        return gate;
    }

    /**
     * Actor getter
     * @return the Actor
     */
    public IActor<M,T,C,S,E,X> getActor(){
        return actor;
    }
}
