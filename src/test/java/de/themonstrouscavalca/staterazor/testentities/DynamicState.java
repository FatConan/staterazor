package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.machine.abs.AbstractDefaultStateMachineBuilder;
import de.themonstrouscavalca.staterazor.machine.impl.DefaultStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;
import de.themonstrouscavalca.staterazor.transition.impl.TransitionScope;

public class DynamicState extends DefaultStateManager<DynamicState, //StateMachine class
        Transition<DynamicState, TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>, //Transition class
        TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext> //State, Event and Event context classes
        implements IStateMachine<DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>{

    static class Builder extends AbstractDefaultStateMachineBuilder<DynamicState,
            Transition<DynamicState, TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>,
            TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>{

        DynamicStateType type = null;
        DynamicState previousState = null;

        public DynamicState.Builder type(DynamicStateType type){
            this.type = type;
            return this;
        }

        public DynamicState.Builder previous(DynamicState previous){
            this.previousState = previous;
            return this;
        }

        @Override
        public DynamicState build(){
            return new DynamicState(this);
        }
    }

    private DynamicStateType type;
    private DynamicState previous;
    private DynamicState next;

    DynamicState(Builder builder){
        this.setName(builder.name);
        this.setState(builder.initialState);
        this.setTransitions(builder.transitions);
        this.type = builder.type;
        this.previous = builder.previousState;
    }

    public DynamicStateType getType(){
        return type;
    }

    public void setType(DynamicStateType type){
        this.type = type;
    }

    public DynamicState getPrevious(){
        return previous;
    }

    public void setPrevious(DynamicState previous){
        this.previous = previous;
    }

    public DynamicState getNext(){
        return next;
    }

    public void setNext(DynamicState next){
        this.next = next;
    }

    public static DynamicState instance(DynamicStateType type, DynamicState previous){
        DynamicState state = new Builder()
                .type(type)//TODO - this is a wart, how can I extend the builder without losing the modifications as it's passed back?
                .previous(previous)
                .name(type.name())
                .initialState(DynamicInternalWorkingState.START)
                .transition(new Transition.Builder<DynamicState, TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>()
                            .from(DynamicInternalWorkingState.START)
                            .to(DynamicInternalWorkingState.PROGRESS)
                            .on(DynamicEvent.PROGRESS)
                            .build(),
                        (t, ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<DynamicState, TransitionScope, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>()
                                .from(DynamicInternalWorkingState.PROGRESS)
                                .to(DynamicInternalWorkingState.COMPLETE)
                                .on(DynamicEvent.COMPLETE)
                                .build(),
                        (t, ic) -> true,
                        ChangeContext::new)
                .build();
        state.setMachine(state); //TODO - This is a wart, how can I remove this?
        return state;
    }

    public static DynamicState instance(DynamicStateType type){
        return instance(type, null);
    }
}
