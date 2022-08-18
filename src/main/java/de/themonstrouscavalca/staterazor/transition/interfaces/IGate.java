package de.themonstrouscavalca.staterazor.transition.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.context.interfaces.IInitialContext;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 * Defines a gate check that determines whether the transition can occur or not (beyond the from-state being correct)
 *
 * @param <M> The state machine class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The vent context class
 */
@FunctionalInterface
public interface IGate<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>{
    boolean permit(T transition, IInitialContext<M, S, E, X> initialContext);
}
