import model.interfaces.FiniteAutomata;
import model.interfaces.State;
import model.implementation.FiniteAutomataImpl;
import model.implementation.StateImpl;
import model.implementation.TransitionImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static Map<String, State> stateMap = new HashMap<>();
    static List<String> alphabetList = new ArrayList<>();
    static String initialState;

    public static void main(String[] args) throws IOException {
        readFiniteAutomata("FA2.in");
        printMenu();
    }


    private static void printMenu() throws IOException {
        String menuString = "1. Display the set of states\n2. Display the alphabet\n3. Display all the transitions\n4. Display the set of final states\n5. Verify if a sequence is accepted by the DFA\n6. Display the initial state";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(menuString);
        while (true) {
            String userInput = br.readLine();
            StringBuilder output = new StringBuilder();
            switch (userInput) {
                case "1" -> {
                    output.append("Set of states is: ");
                    for (String state : stateMap.keySet()) {
                        output.append(state).append(" ");
                    }
                }
                case "2" -> {
                    output.append("Alphabet is: ");
                    for (String element : alphabetList) {
                        output.append(element).append(" ");
                    }
                }
                case "3" -> {
                    output.append("Transitions are:\n");
                    stateMap.forEach((stateName, state) -> state.getTransitions().forEach(transition -> output.append(String.format("\t(%s, %s)->%s%n",
                            stateName,
                            transition.getAction(),
                            stateMap.entrySet()
                                    .stream()
                                    .filter(entry -> transition.getNextState().equals(entry.getValue()))
                                    .map(Map.Entry::getKey)
                                    .findAny()
                                    .orElse("Invalid")))));
                }
                case "4" -> {
                    output.append("Final states are: ");
                    stateMap.forEach((key, value) -> {
                        if (value.isFinal()) {
                            output.append(key).append(" ");
                        }
                    });
                }
                case "5" -> {
                    FiniteAutomata finiteAutomata = new FiniteAutomataImpl(stateMap.get(initialState));
                    System.out.println("Input sequence: ");
                    String sequence = br.readLine();
                    for (int i = 0; i < sequence.length(); i++) {
                        try {
                            finiteAutomata = finiteAutomata.switchState(String.valueOf(sequence.charAt(i)));
                        } catch (Exception e) {
                            output.append(String.format("Exception at token %d: %s%n", i + 1, e.getMessage()));
                            System.out.println(output);
                            return;
                        }
                    }
                    if(finiteAutomata.canStop()){
                        output.append("Sequence is accepted!");
                    }
                    else {
                        output.append("Sequence not accepted; stack is empty, but we are not on final state!");
                    }
                }
                case "6" -> output.append("Initial state is: ").append(initialState);
                default -> output.append("invalid choice!");
            }
            System.out.println(output);
        }
    }

    private static void readFiniteAutomata(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().trim();
                processLine(line);
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processLine(String line) throws Exception {
        String[] tokens = line.split("=");
        if (tokens.length != 2) {
            throw new Exception("Invalid input!");
        }
        switch (tokens[0].trim()) {
            case "Q" -> {
                String[] stateTokens = getTokens(tokens[1].trim(), ",");
                for (String state : stateTokens) {
                    stateMap.put(state.trim(), new StateImpl());
                }
            }
            case "E" -> {
                String[] alphabet = getTokens(tokens[1].trim(), ",");
                for (String element : alphabet) {
                    alphabetList.add(element.trim());
                }
            }
            case "q0" -> {
                String[] initialStateTokens = getTokens(tokens[1].trim(), ",");
                if (initialStateTokens.length != 1) {
                    throw new Exception("Invalid initial state!");
                }
                initialState = initialStateTokens[0];
            }
            case "F" -> {
                String[] finalStates = getTokens(tokens[1].trim(), ",");
                for (String state : finalStates) {
                    stateMap.get(state).setFinal(true);
                }
            }
            case "S" -> {
                String[] transitions = getTokens(tokens[1].trim(), ";");
                for (String transitionToken : transitions) {
                    String[] transitionElements = transitionToken.trim().split("->");
                    String transition = transitionElements[0].trim();
                    String resultState = transitionElements[1].trim();
                    String firstState = transition.split(",")[0].trim();
                    String action = transition.split(",")[1].trim();
                    stateMap.get(firstState).addTransition(new TransitionImpl(action, stateMap.get(resultState)));
                }
            }
            default -> throw new Exception("Invalid input file!");
        }
    }

    private static String[] getTokens(String tokenString, String separator) {
        String[] result = tokenString.trim().split(separator);
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
    }
}
