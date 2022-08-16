package de.themonstrouscavalca.staterazor.testentities;

import de.themonstrouscavalca.staterazor.context.impl.ChangeContext;
import de.themonstrouscavalca.staterazor.machine.abs.AbstractDefaultStateMachineBuilder;
import de.themonstrouscavalca.staterazor.machine.impl.DefaultStateManager;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.transition.impl.Transition;

public class DynamicState extends DefaultStateManager<DynamicState, //StateMachine class
        Transition<DynamicState, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>, //Transition class
        DynamicInternalWorkingState, DynamicEvent, DynamicEventContext> //State, Event and Event context classes
        implements IStateMachine<DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>{

    static class Builder extends AbstractDefaultStateMachineBuilder<DynamicState,
            Transition<DynamicState, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>,
            DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>{

        DynamicStateType type = null;
        DynamicState previousState = null;

        public Builder type(DynamicStateType type){
            this.type = type;
            return this;
        }

        public Builder previous(DynamicState previous){
            this.previousState = previous;
            return this;
        }

        @Override
        public DynamicState build(){
            return new DynamicState(this);
        }
    }

    private final DynamicStateType type;
    private final DynamicState previous;

    DynamicState(Builder builder){
        this.setName(builder.name);
        this.setState(builder.initialState);
        this.type = builder.type;
        this.previous = builder.previousState;
    }

    public DynamicStateType getType(){
        return type;
    }

    public DynamicState getPrevious(){
        return previous;
    }

    public static DynamicState instance(DynamicStateType type, DynamicState previous){
        return new Builder()
                .type(type)
                .previous(previous)
                .name(type.name())
                .initialState(DynamicInternalWorkingState.START)
                .transition(new Transition.Builder<DynamicState, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>()
                            .from(DynamicInternalWorkingState.START)
                            .to(DynamicInternalWorkingState.PROGRESS)
                            .on(DynamicEvent.PROGRESS)
                            .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .transition(new Transition.Builder<DynamicState, DynamicInternalWorkingState, DynamicEvent, DynamicEventContext>()
                                .from(DynamicInternalWorkingState.PROGRESS)
                                .to(DynamicInternalWorkingState.COMPLETE)
                                .on(DynamicEvent.COMPLETE)
                                .build(),
                        (ic) -> true,
                        ChangeContext::new)
                .build();
    }

    public static DynamicState instance(DynamicStateType type){
        return instance(type, null);
    }
}
