package model.interfaces;

public interface FiniteAutomata {
    FiniteAutomata switchState(CharSequence c) throws Exception;
    boolean canStop();
}
