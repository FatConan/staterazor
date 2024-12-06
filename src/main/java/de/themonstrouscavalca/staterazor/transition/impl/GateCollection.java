package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.context.interfaces.IInitialContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.IGate;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GateCollection<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X> implements IGate<M, T, C, S, E, X> {

    private final List<IGate<M, T, C, S, E, X>> gates;

    @SafeVarargs
    public GateCollection(IGate<M, T, C, S, E, X>... gates){
        this.gates = new ArrayList<>();
        Collections.addAll(this.gates, gates);
    }

    @Override
    public boolean permit(T transition, IInitialContext<M, S, E, X> initialContext) {
        for(IGate<M, T, C, S, E, X> gate : gates){
            if(!gate.permit(transition, initialContext)){
                return false;
            }
        }
        return true;
    }
}
