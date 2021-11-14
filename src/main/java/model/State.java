package model;

import java.util.List;

public interface State {
    State addTransition(Transition transition);
    State performTransition(CharSequence c) throws Exception;
    boolean isFinal();
    void setFinal(boolean value);
    List<Transition> getTransitions();
}
