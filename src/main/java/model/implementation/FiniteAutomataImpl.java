package model.implementation;

import model.FiniteAutomata;
import model.State;

public class FiniteAutomataImpl implements FiniteAutomata {
    private State currentState;

    public FiniteAutomataImpl(State initialState){
        this.currentState = initialState;
    }

    @Override
    public FiniteAutomata switchState(CharSequence c) throws Exception {
        return new FiniteAutomataImpl(this.currentState.performTransition(c));
    }

    @Override
    public boolean canStop() {
        return this.currentState.isFinal();
    }
}
