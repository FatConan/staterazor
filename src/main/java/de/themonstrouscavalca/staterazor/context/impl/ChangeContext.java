package de.themonstrouscavalca.staterazor.context.impl;

import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IInitialContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

public class ChangeContext<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>
        extends InitialContext<M, S, E, X>
        implements IChangeContext<M, S, E, X>{

    public ChangeContext(){
        //pass
    }

    public ChangeContext(IInitialContext<M, S, E, X> initial){
        this.setFromState(initial.getFromState());
        this.setEvent(initial.getEvent());
        this.setEventContext(this.getEventContext());
        this.setMachine(this.getMachine());
    }

    public ChangeContext(S toState, IInitialContext<M, S, E, X> initial){
        this(initial);
        this.setToState(toState);
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

