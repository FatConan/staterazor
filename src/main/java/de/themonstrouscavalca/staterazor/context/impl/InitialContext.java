package de.themonstrouscavalca.staterazor.context.impl;

import de.themonstrouscavalca.staterazor.context.interfaces.IInitialContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;

public class InitialContext<M extends IStateMachine<S, E, X>, S extends IState, E extends IEvent, X>
        implements IInitialContext<M, S, E, X>{

    private M machine;
    private S fromState;
    private E event;
    private X eventContext;

    @Override
    public M getMachine(){
        return this.machine;
    }

    @Override
    public S getFromState(){
        return this.fromState;
    }

    @Override
    public E getEvent(){
        return this.event;
    }

    @Override
    public X getEventContext(){
        return this.eventContext;
    }

    @Override
    public void setMachine(M machine){
        this.machine = machine;
    }

    @Override
    public void setFromState(S state){
        this.fromState = state;
    }

    @Override
    public void setEvent(E event){
        this.event = event;
    }

    @Override
    public void setEventContext(X eventContext){
        this.eventContext = eventContext;
    }

    @Override
    public boolean hasMachine(){
        return this.machine != null;
    }

    @Override
    public boolean hasFromState(){
        return this.fromState != null;
    }

    @Override
    public boolean hasEvent(){
        return this.event != null;
    }

    @Override
    public boolean hasEventContext(){
        return this.eventContext != null;
    }
}
