package de.themonstrouscavalca.staterazor.state.impl;

import de.themonstrouscavalca.staterazor.state.interfaces.IState;

/**
 *
 */
public class DummyState implements IState{
    private String name;

    @Override
    public String name(){
        return name;
    }

    /**
     *
     * @param name
     * @return
     */
    public static DummyState of(String name){
        DummyState d = new DummyState();
        d.name = name;
        return d;
    }
}
