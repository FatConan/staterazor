package de.themonstrouscavalca.staterazor.state.interfaces;


import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;

/**
 * This defines a generator for transitions that generate their to state when called. This allows for the construction
 * of dynamic state machines that can react to demands on the fly, rather than relying on statically defined states. This
 * is useful for modelling process that have flexible pathways through them where steps may be added or skipped.
 *
 * @param <M> The state machine class
 * @param <S> The state class
 * @param <E> The event class
 * @param <X> The vent context class
 */
@FunctionalInterface
public interface IGenerateState<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>{
    S generate(M machine, E event, X eventContext);
}
