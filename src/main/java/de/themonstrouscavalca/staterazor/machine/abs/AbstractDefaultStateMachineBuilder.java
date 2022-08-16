package de.themonstrouscavalca.staterazor.machine.abs;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IBuildStateMachines;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.IActor;
import de.themonstrouscavalca.staterazor.transition.interfaces.IGate;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;

/** A default implementation of a state machine builder that will construct a state machine using DefaultTransitions
 * which are the default implementation of Transitions matching the ITransition interface
 *
 * @param <M> The state machine class (that'll implement IStateMachine)
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The event context class
 */
public abstract class AbstractDefaultStateMachineBuilder<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, S, E, X>,
        S extends IState, E extends IEvent, X>
        implements IBuildStateMachines<M, S, E, X>{

    public S initialState;
    public String name;
    public ITransitionMap<M, T, S, E, X> transitions = new TransitionMap<>();

    public AbstractDefaultStateMachineBuilder<M, T, S, E, X>  name(String name){
        this.name = name;
        return this;
    }

    public AbstractDefaultStateMachineBuilder<M, T, S, E, X> initialState(S initialState){
        this.initialState = initialState;
        return this;
    }

    public AbstractDefaultStateMachineBuilder<M, T, S, E, X> transition(T transition,
                                                                        IGate<M, S, E, X> gate,
                                                                        IActor<M, S, E, X> actor){
        this.transitions.add(transition, gate, actor);
        return this;
    }
}
