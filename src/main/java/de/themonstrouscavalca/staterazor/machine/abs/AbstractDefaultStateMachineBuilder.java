package de.themonstrouscavalca.staterazor.machine.abs;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IBuildStateMachines;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.*;

/** A default implementation of a state machine builder that will construct a state machine using DefaultTransitions
 * which are the default implementation of Transitions matching the ITransition interface
 *
 * @param <M> The state machine class (that'll implement IStateMachine)
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The event context class
 */
public abstract class AbstractDefaultStateMachineBuilder<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState,
        E extends IEvent, X>
        implements IBuildStateMachines<M, S, E, X>{

    public S initialState;
    public String name;
    public ITransitionMap<M, T, C, S, E, X> transitions = new TransitionMap<>();

    public AbstractDefaultStateMachineBuilder<M, T, C, S, E, X>  name(String name){
        this.name = name;
        return this;
    }

    public AbstractDefaultStateMachineBuilder<M, T, C, S, E, X> initialState(S initialState){
        this.initialState = initialState;
        return this;
    }

    public AbstractDefaultStateMachineBuilder<M, T, C, S, E, X> transition(T transition,
                                                                        IGate<M, S, E, X> gate,
                                                                        IActor<M, S, E, X> actor){
        this.transitions.add(transition, gate, actor);
        return this;
    }
}
