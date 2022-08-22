package de.themonstrouscavalca.staterazor.transition.impl;

import de.themonstrouscavalca.staterazor.state.impl.DummyState;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionStates;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransitionStates<S extends IState> extends HashSet<S> implements ITransitionStates<S>{
    @SuppressWarnings("rawtypes")
    public static final TransitionStates WILDCARD;
    static {
        @SuppressWarnings("rawtypes")
        final TransitionStates wildcard = new TransitionStates<>();
        wildcard.wildcard = true;
        WILDCARD = wildcard;
    }

    @SuppressWarnings("unchecked")
    public static <S extends IState> TransitionStates<S> wildcard() {
        return (TransitionStates<S>) WILDCARD;
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <S extends IState> TransitionStates<S> of(S ... states) {
        TransitionStates<S> stateCollection = new TransitionStates<>();
        stateCollection.addAll(Arrays.asList(states));
        return stateCollection;
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <S extends IState> TransitionStates<S> not(S ... states) {
        TransitionStates<S> stateCollection = new TransitionStates<>();
        stateCollection.addAll(Arrays.asList(states));
        stateCollection.inverse = true;
        return stateCollection;
    }

    public static  <S extends IState> TransitionStates<S> not(String ... states) {
        TransitionStates<S> stateCollection = new TransitionStates<>();
        stateCollection.addAll((Collection<? extends S>) Stream.of(states).map(DummyState::of).collect(Collectors.toList()));
        stateCollection.dummyStates = true;
        stateCollection.inverse = true;
        return stateCollection;
    }

    public static  <S extends IState> TransitionStates<S> of(String ... states) {
        TransitionStates<S> stateCollection = new TransitionStates<>();
        stateCollection.addAll((Collection<? extends S>) Stream.of(states).map(DummyState::of).collect(Collectors.toList()));
        stateCollection.dummyStates = true;
        return stateCollection;
    }

    private boolean dummyStates = false;
    private boolean wildcard = false;
    private boolean inverse = false;

    @Override
    public boolean isWildcard(){
        return wildcard;
    }

    @Override
    public boolean isInverse(){
        return inverse;
    }

    @Override
    public boolean isDummy(){
        return dummyStates;
    }

    @Override
    public boolean matches(S state){
        if(this.isWildcard()){
            //Wild cards match any state, so return true without performing further checks
            return true;
        }

        if(this.isDummy()){
            if(this.isInverse()){
                return !this.stream().map(IState::name).collect(Collectors.toList()).contains(state.name());
            }
            return this.stream().map(IState::name).collect(Collectors.toList()).contains(state.name());
        }

        if(this.isInverse()){
            //This is a NOT group. check that the provided state is NOT in the current set
            return !this.contains(state);
        }
        //Otherwise check if the returned state is within the current set
        return this.contains(state);
    }
}
