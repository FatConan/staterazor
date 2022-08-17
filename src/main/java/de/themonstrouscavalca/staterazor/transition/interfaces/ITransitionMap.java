package de.themonstrouscavalca.staterazor.transition.interfaces;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.impl.GateAndActor;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;

import java.util.List;
import java.util.Set;

public interface ITransitionMap<
        M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>{
    Set<E> getDefinedEvents();
    GateAndActor<M, S, E, X> get(T transition);
    List<T> forEventAndState(E event, S state);
    void add(T transition, IGate<M, S, E, X> gate, IActor<M, S, E, X> actor);
}
