package model;

public interface FiniteAutomata {
    FiniteAutomata switchState(CharSequence c) throws Exception;
    boolean canStop();
}
