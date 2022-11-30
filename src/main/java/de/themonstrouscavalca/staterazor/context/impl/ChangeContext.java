package de.themonstrouscavalca.staterazor.context.impl;

import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IInitialContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

public class ChangeContext<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>
        extends InitialContext<M, S, E, X>
        implements IChangeContext<M, S, E, X>{

    public ChangeContext(){
        //pass
    }

    public ChangeContext(IInitialContext<M, S, E, X> initial){
        this.setFromState(initial.getFromState());
        this.setEvent(initial.getEvent());
        this.setEventContext(initial.getEventContext());
        this.setMachine(initial.getMachine());
    }

    public ChangeContext(S toState, IInitialContext<M, S, E, X> initial){
        this(initial);
        this.setToState(toState);
    }

    public <T extends ITransition<M, C, S, E, X>,
            C extends ITransitionScope> ChangeContext(T ignored, S toState, IInitialContext<M, S, E, X> initial){
        this(toState, initial);
    }

    private S toState;

    @Override
    public S getToState(){
        return this.toState;
    }

    @Override
    public void setToState(S state){
        this.toState = state;
    }

    @Override
    public boolean hasToState(){
        return this.toState != null;
    }
}

