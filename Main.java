import java.util.Scanner;

import java.util.*;

public class Main {

    private static Map<String, Map<Character, Set<String>>> nfa = new HashMap<>();

    // Set of accepting states
    private static Set<String> acceptingStates = new HashSet<>();

    public static void main(String[] args) {
        // Define NFA transitions
        addTransition("q0", 'a', "q0", "q1");
        addTransition("q0", 'b', "q0");
        addTransition("q1", 'b', "q2");
        addTransition("q1", 'a', "q1");
        addTransition("q2", 'a', "q2");
        addTransition("q2", 'b', "q2");

        // Accepting state
        acceptingStates.add("q2");

        Scanner sc = new Scanner(System.in);
        System.out.print("Input: ");
        String input = sc.nextLine();

        // Start from q0
        Set<String> startStates = new HashSet<>();
        startStates.add("q0");

        // NFA simulation
        boolean accepted = simulateNFA(startStates, input, 0);

        System.out.println(accepted ? "Output: Accepted" : "Output: Rejected");
        sc.close();
    }

    // Add transitions to the NFA
    private static void addTransition(String from, char symbol, String... toStates) {
        nfa.putIfAbsent(from, new HashMap<>());
        nfa.get(from).putIfAbsent(symbol, new HashSet<>());
        nfa.get(from).get(symbol).addAll(Arrays.asList(toStates));
    }

    // Recursive simulation of NFA
    private static boolean simulateNFA(Set<String> currentStates, String input, int index) {
        // Check if any state is accepting after all input is consumed
        if (index == input.length()) {
            for (String state : currentStates) {
                if (acceptingStates.contains(state)) {
                    return true;
                }
            }
            return false;
        }

        char symbol = input.charAt(index);
        Set<String> nextStates = new HashSet<>();

        // Simulate all possible transitions
        for (String state : currentStates) {
            Map<Character, Set<String>> transitions = nfa.get(state);
            if (transitions != null && transitions.containsKey(symbol)) {
                nextStates.addAll(transitions.get(symbol));
            }
        }

        if (nextStates.isEmpty()) return false;
        return simulateNFA(nextStates, input, index + 1);
    }
}