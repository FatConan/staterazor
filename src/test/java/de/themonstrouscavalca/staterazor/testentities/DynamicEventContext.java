package de.themonstrouscavalca.staterazor.testentities;

public class DynamicEventContext{
    private DynamicStateType stateType;

    public DynamicStateType getStateType(){
        return stateType;
    }

    public void setStateType(DynamicStateType stateType){
        this.stateType = stateType;
    }

    public static DynamicEventContext of(DynamicStateType type){
        DynamicEventContext context = new DynamicEventContext();
        context.setStateType(type);
        return context;
    }
}
