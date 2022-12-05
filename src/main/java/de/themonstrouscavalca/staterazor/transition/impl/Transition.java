package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IGenerateState;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionStates;

import java.util.Objects;

public class Transition<
        M extends IStateMachine<S, E, X>,
        C extends ITransitionScope,
        S extends IState,
        E extends IEvent, X> implements ITransition<M, C, S, E, X>{

    private static final String FROM_STATES_ERROR = "This transition has been configured to use complex from states, use getFromStates()";
    private static final String FROM_STATE_ERROR = "This transition has been configured to use a simple from state, use getFromState()";
    private static final String STATE_GENERATOR_ERROR = "This transition has been configured to use a dynamic to state generator, use getToState(event, context, eventContext)";

    private final String name;
    private final boolean internal;
    private final boolean dynamic;
    private final boolean multipleOrigin;
    private final TransitionStates<S> fromStates;
    private final C scope;
    private final S fromState;
    private final IGenerateState<M, S, E, X> toStateGenerator;
    private final S toState;
    private final E event;

    public static class Builder<
            M extends IStateMachine<S, E, X>,
            C extends ITransitionScope,
            S extends IState,
            E extends IEvent, X>{
        private String name = "";

        private boolean internal = false;
        private boolean multipleOrigin = false;
        private boolean dynamic = false;

        private TransitionStates<S> fromStates;
        private S fromState;

        private IGenerateState<M, S, E, X> toStateGenerator;
        private S toState;

        private C scope;
        private E event;
        private boolean privileged = false;

        public Builder<M, C, S, E, X> scope(C scope){
            this.scope = scope;
            return this;
        }

        public Builder<M, C, S, E, X> from(TransitionStates<S> fromStates){
            this.multipleOrigin = true;
            this.fromStates = fromStates;
            return this;
        }
        public Builder<M, C, S, E, X> from(S fromState){
            this.fromState = fromState;
            return this;
        }
        public Builder<M, C, S, E, X> to(S toState){
            this.toState = toState;
            return this;
        }
        public Builder<M, C, S, E, X> to(IGenerateState<M, S, E, X> toStateGenerator){
            this.dynamic = true;
            this.toStateGenerator = toStateGenerator;
            return this;
        }
        public Builder<M, C, S, E, X> internal(TransitionStates<S> fromStates){
            return this.from(fromStates);
        }
        public Builder<M, C, S, E, X> internal(S fromState){
            return this.from(fromState);
        }
        public Builder<M, C, S, E, X> name(String name){
            this.name = name;
            return this;
        }
        public Builder<M, C, S, E, X> on(E onEvent){
            this.event = onEvent;
            return this;
        }

        boolean isInternal(){
            //If our to and from states match, or whe have from states but no to state or to state generator then we
            //consider ti to be an internal transition
            return ((this.fromState != null && this.toState != null ) && this.fromState == this.toState)
                    || (this.fromStates != null && (this.toState == null && this.toStateGenerator == null));
        }

        public Transition<M, C, S, E, X> build(){
            return new Transition<>(this);
        }
    }

    public Transition(Builder<M, C, S, E, X> builder){
        this.name = builder.name;
        this.scope = builder.scope;
        this.multipleOrigin = builder.multipleOrigin;
        this.dynamic = builder.dynamic;
        this.internal = builder.isInternal();

        this.fromState = builder.fromState;
        this.fromStates = builder.fromStates;
        this.toStateGenerator = builder.toStateGenerator;
        this.toState = builder.toState;
        this.event = builder.event;
    }

    @Override
    public String name(){
        return this.name;
    }

    @Override
    public boolean isInternal(){
        return this.internal;
    }

    @Override
    public boolean isExternal(){
        return !this.internal;
    }

    @Override
    public boolean isDynamic(){
        return this.dynamic;
    }

    @Override
    public boolean isStatic(){
        return !this.dynamic;
    }

    @Override
    public boolean multipleOrigin(){
        return this.multipleOrigin;
    }

    @Override
    public boolean singleOrigin(){
        return !this.multipleOrigin;
    }

    @Override
    public boolean matchesFromState(S state){
        return this.multipleOrigin() ? this.getFromStates().matches(state) : Objects.equals(this.getFromState(), state);
    }

    @Override
    public C getScope(){
        return this.scope;
    }

    @Override
    public S getFromState(){
        return this.fromState;
    }

    @Override
    public ITransitionStates<S> getFromStates(){
        return this.fromStates;
    }

    @Override
    public IGenerateState<M, S, E, X> getToStateGenerator(){
        return this.toStateGenerator;
    }

    @Override
    public S getToState(M machine, S fromState, E event, X eventContext){
        if(this.isDynamic()){
            return this.toStateGenerator.generate(machine, event, eventContext);
        }
        return this.toState;
    }

    @Override
    public S getToState(){
        return this.toState;
    }

    @Override
    public E getEvent(){
        return this.event;
    }
}
