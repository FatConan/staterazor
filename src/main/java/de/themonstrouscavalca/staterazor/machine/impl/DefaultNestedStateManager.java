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

public class DefaultNestedStateManager<
        MT extends IStateMachine<ST, E, X>,
        MS extends IStateMachine<SS, E, X>,
        TT extends ITransition<MT, CT, ST, E, X>,
        TS extends ITransition<MS, CS, SS, E, X>,
        ST extends IManageStates<MS, TS, CS, SS, E, X>,
        SS extends IState,
        CT extends ITransitionScope,
        CS extends ITransitionScope,
        E extends IEvent, X>
        implements IManageStates<MT ,TT, CT, ST, E, X>{

    private MT machine;
    private String name;
    private ST state;
    private ITransitionMap<MT, TT, CT, ST, E, X> transitions;

    public String name(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ST state(){
        return state;
    }

    public void setState(ST state){
        this.state = state;
    }

    public void setTransitions(ITransitionMap<MT, TT, CT, ST, E, X> transitions){
        this.transitions = transitions;
    }

    public MT getMachine(){
        return machine;
    }

    public void setMachine(MT machine){
        this.machine = machine;
    }

    @Override
    public ITransitionMap<MT, TT, CT, ST, E, X> getTransitions(){
        return this.transitions;
    }

    @Override
    public List<TT> potentialTransitions(){
        return this.transitions.forState(this.state());
    }

    @Override
    public List<TT> potentialTransitions(CT scope){
        return this.transitions.forState(this.state()).stream().filter(t -> Objects.equals(t.getScope(), scope)).collect(Collectors.toList());
    }

    protected List<TT> validatedTransitions(List<TT> potentialTransitions, X eventContext){
        List<TT> valid = new ArrayList<>();
        for(TT transition: potentialTransitions){
            if(this.validateTransition(transition, eventContext)){
                valid.add(transition);
            }
        }
        return valid;
    }

    @Override
    public Boolean validateTransition(TT transition, X eventContext){
        GateAndActor<MT, TT, CT, ST, E, X> gateAndActor = this.transitions.get(transition);
        return (gateAndActor != null && gateAndActor.gate().permit(transition, this.initialContext(transition.getEvent(), eventContext)));
    }

    @Override
    public List<TT> validTransitions(X eventContext){
        List<TT> potentialTransitions = this.potentialTransitions();
        return this.validatedTransitions(potentialTransitions, eventContext);
    }

    @Override
    public List<TT> validTransitions(X eventContext, CT scope){
        List<TT> potentialTransitions = this.potentialTransitions(scope);
        return this.validatedTransitions(potentialTransitions, eventContext);
    }

    protected InitialContext<MT, ST, E, X> initialContext(E event, X eventContext){
        InitialContext<MT, ST, E, X> initialContext = new InitialContext<>();
        initialContext.setMachine(this.machine);
        initialContext.setFromState(this.state());
        initialContext.setEvent(event);
        initialContext.setEventContext(eventContext);
        return initialContext;
    }

    protected IMonitorChange<MT, TT, CT, ST, E, X> handleEvent(E event, X eventContext){
        if(this.state() != null){
            IMonitorChange<MS, TS, CS, SS, E, X> passDown = this.state().onEvent(event, eventContext);
            //If our result is not null and not empty we don't do any further transitions
            if(passDown != null && !passDown.isEmpty()){
                //TODO - What is the correct thing to do here? We could wrap it somehow?
                return ChangeMonitor.empty();
            }
        }

        InitialContext<MT, ST, E, X> initialContext = this.initialContext(event, eventContext);

        List<TT> selected = this.getTransitions()
                .forEventAndState(event, initialContext.getFromState());

        Optional<TT> selectedOpt =
                selected.stream().filter(t -> this.getTransitions()
                                .get(t).gate().permit(t, initialContext))
                        .findFirst();

        if(selectedOpt.isPresent()){
            TT transition = selectedOpt.get();
            GateAndActor<MT, TT, CT, ST, E, X> gateAndActor = this.getTransitions().get(transition);
            ST solidifiedFromState = initialContext.getFromState();
            ST solidifiedToState = selectedOpt.get().getToState(this.machine, initialContext.getFromState(), event, eventContext);

            this.setState(solidifiedToState);
            IChangeContext<MT, ST, E, X> changeContext = gateAndActor.getActor().act(transition, solidifiedToState, initialContext);
            changeContext.setFromState(solidifiedFromState);
            changeContext.setToState(solidifiedToState);
            return ChangeMonitor.transitioned(transition, changeContext);
        }

        return ChangeMonitor.empty();
    }

    public IMonitorChange<MT, TT, CT, ST, E, X> onEvent(E event, X eventContext){
        return this.handleEvent(event, eventContext);
    }

    public IMonitorChange<MT, TT, CT, ST, E, X> onEvent(E event){
        return this.onEvent(event, null);
    }
}
