package de.themonstrouscavalca.staterazor.machine.impl;

import de.themonstrouscavalca.staterazor.context.impl.InitialContext;
import de.themonstrouscavalca.staterazor.context.interfaces.IChangeContext;
import de.themonstrouscavalca.staterazor.events.interfaces.IEvent;
import de.themonstrouscavalca.staterazor.machine.interfaces.IManageStates;
import de.themonstrouscavalca.staterazor.machine.interfaces.IStateMachine;
import de.themonstrouscavalca.staterazor.state.interfaces.IState;
import de.themonstrouscavalca.staterazor.transition.impl.ChangeMonitor;
import de.themonstrouscavalca.staterazor.transition.impl.GateAndActor;
import de.themonstrouscavalca.staterazor.transition.interfaces.IMonitorChange;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransition;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionMap;
import de.themonstrouscavalca.staterazor.transition.interfaces.ITransitionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A default implementation of a state machine, that holds all states, the definition of the machine itself, the transitions
 * and gates and handles all of events in a standard way. This Default Machine can act as the basis of any state machine you wish to build
 * unless you'd like to handle transitions in your own way in which case you can roll a state machine from scratch so long as it
 * satisfies the interfaces.
 * @param <M>
 * @param <T>
 * @param <C>
 * @param <S>
 * @param <E>
 * @param <X>
 */
public class DefaultStateManager<M extends IStateMachine<S, E, X>,
        T extends ITransition<M, C, S, E, X>,
        C extends ITransitionScope,
        S extends IState, E extends IEvent, X>
    implements IManageStates<M ,T, C, S, E, X>{

    private M machine;
    private String name;
    private S state;
    private ITransitionMap<M, T, C, S, E, X> transitions;

    public String name(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public S state(){
        return state;
    }

    public void setState(S state){
        this.state = state;
    }

    public void setTransitions(ITransitionMap<M, T, C, S, E, X> transitions){
        this.transitions = transitions;
    }

    public M getMachine(){
        return machine;
    }

    public void setMachine(M machine){
        this.machine = machine;
    }

    @Override
    public ITransitionMap<M, T, C, S, E, X> getTransitions(){
        return this.transitions;
    }

    @Override
    public List<T> potentialTransitions(){
        return this.transitions.forState(this.state());
    }

    @Override
    public List<T> potentialTransitions(C scope){
        return this.transitions.forState(this.state()).stream().filter(t -> Objects.equals(t.getScope(), scope)).collect(Collectors.toList());
    }

    protected List<T> validateTransitions(List<T> potentialTransitions, X eventContext){
        List<T> valid = new ArrayList<>();
        for(T transition: potentialTransitions){
            if(this.validateTransition(transition, eventContext)){
                valid.add(transition);
            }
        }
        return valid;
    }

    @Override
    public Boolean validateTransition(T transition, X eventContext){
        GateAndActor<M, T, C, S, E, X> gateAndActor = this.transitions.get(transition);
        return (gateAndActor != null && gateAndActor.gate().permit(transition, this.initialContext(transition.getEvent(), eventContext)));
    }

    @Override
    public List<T> validTransitions(X eventContext){
        List<T> potentialTransitions = this.potentialTransitions();
        return this.validateTransitions(potentialTransitions, eventContext);
    }

    @Override
    public List<T> validTransitions(X eventContext, C scope){
        List<T> potentialTransitions = this.potentialTransitions(scope);
        return this.validateTransitions(potentialTransitions, eventContext);
    }

    protected InitialContext<M, S, E, X> initialContext(E event, X eventContext){
        InitialContext<M, S, E, X> initialContext = new InitialContext<>();
        initialContext.setMachine(this.machine);
        initialContext.setFromState(this.state());
        initialContext.setEvent(event);
        initialContext.setEventContext(eventContext);
        return initialContext;
    }

    protected IMonitorChange<M, T, C, S, E, X> handleEvent(E event, X eventContext){
        InitialContext<M, S, E, X> initialContext = this.initialContext(event, eventContext);

        List<T> selected = this.getTransitions()
                .forEventAndState(event, initialContext.getFromState());

        Optional<T> selectedOpt =
                selected.stream().filter(t -> this.getTransitions()
                                .get(t).gate().permit(t, initialContext))
                        .findFirst();

        if(selectedOpt.isPresent()){
            T transition = selectedOpt.get();
            GateAndActor<M,T,C,S,E,X> gateAndActor = this.getTransitions().get(transition);
            S solidifiedFromState = initialContext.getFromState();
            S solidifiedToState = selectedOpt.get().getToState(this.machine, initialContext.getFromState(), event, eventContext);

            this.setState(solidifiedToState);
            IChangeContext<M, S, E, X> changeContext = gateAndActor.getActor().act(transition, solidifiedToState, initialContext);
            changeContext.setFromState(solidifiedFromState);
            changeContext.setToState(solidifiedToState);
            IMonitorChange<M, T, C, S, E, X> monitor = ChangeMonitor.transitioned(selectedOpt.get(), changeContext);
            return monitor;
        }

        IMonitorChange<M, T, C, S, E, X> monitor = ChangeMonitor.empty();
        return monitor;
    }

    public IMonitorChange<M, T, C, S, E, X> onEvent(E event, X eventContext){
        return this.handleEvent(event, eventContext);
    }

    public IMonitorChange<M, T, C, S, E, X> onEvent(E event){
        return this.onEvent(event, null);
    }
}
