/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 * encoding of a DFSA:

    Assume an alphabet sigma = {0,1}
    Assume states are numbered starting at 1.
    <at least one 0 that specifies the number of states>
    <a single 1 acts as a delimiter>
    <number of 0's specifies where the first sate transitions to on 0>
    <a single 1 acts as a delimiter>
    <number of 0's specifies where the first sate transitions to on 1>
    <a single 1 acts as a delimiter>
    <number of 0's specifies where the second sate transitions to on 0>
    <a single 1 acts as a delimiter>
    <number of 0's specifies where the second sate transitions to on 1>
    :
    :
    <two 1's act as a delimiter>
    <number of 0's specify an accepting state>
    <a single 1 acts as a delimiter>
    <number of 0's specify an accepting state>
    :
    :
    <two 1's act as a delimiter>
    The remaining string of 0's and 1's is a string that will be accepted or not.
 * @author ruijieouyang
 */
public class DFSASimulator {

    private boolean[] finalStates;
    private char[][] delta; 
    private int startState;
    private int currentState;
    private int totalStates;
    
    /**
     * This DFSA's states number starts at 1. Since we're using arrays ,
     * deduct 1 to store in array. 
     * @param n
     * @param start the start state, actually is 1, but since we're using array,
     * it shows as start from 0.
     */
    public DFSASimulator(int n, int start) {
        totalStates = n;
        finalStates = new boolean[totalStates];
        delta = new char[totalStates][totalStates];
        startState = start-1;
    }
    
    private boolean isFinal() {
        return finalStates[currentState];
    }

    public void addTransition(int fromState, int toState, char letter) {
        delta[fromState][toState] = letter;
    }

    public void addFinalState(int q) {
        finalStates[q] = true;
    }

    public boolean isAccepted(String s) {
        currentState = startState;
        readingSymbols:
        for (int i = 0; i < s.length(); i++) {
           // System.out.println("current	state: " + currentState);
           // System.out.println("next symbol: " + s.charAt(i));
            for (int j = 0; j < totalStates; j++) {
                if (delta[currentState][j] == s.charAt(i)) {
                    currentState = j;
                    continue readingSymbols;
                }
            }
           // System.out.println("d(" + currentState + "," + s.charAt(i) + ") is undefined");
            return false;
        }
        //System.out.println("final state: " + currentState);
        return isFinal();
    }

    /**
     * Assume input only contains 0 and 1 character.And the input is in correct
     * format that and is suitable for the DFSA encoding principal.
     * @param args 
     */
    public static void main(String args[]) {
        String input = args[0];
        
        //at least one 0 that specifies the number of states,a single 1 acts as a delimiter
        int first1 = input.indexOf("1");
        int totalStates = first1;
        //	Define	the DFSA,starting at 1
        
        DFSASimulator dfsa = new DFSASimulator(totalStates, 1);
        
        
        int i = first1+1;
        int begin0 = i;
        int j=0;
        //two 1's act as a delimiter. Set transitions
        while(!(input.charAt(i)=='1'&& input.charAt(i+1)=='1')){
            if(input.charAt(i)=='1'){
                //get mumber of 0's between two 1's 
                int stateTransNum = i-begin0; 
                //set the number of 0's as transition
                //j/2 will be 0,0,1,1,2,2....
                //j%2 will be 0,1,0,1,0,1...
                dfsa.addTransition(j/2, stateTransNum-1, (""+j%2).charAt(0));
                
                begin0=i+1;
                j++;
            }
            i++;
        }
        //add the last transition
        dfsa.addTransition(j/2, i-begin0-1, (""+j%2).charAt(0));
        
        //set accepting state. get the string between the first 11 and second 11
        //and then split by single 1 as delimiter, then get number of each 0's specify
        //as one accepting state.
        int second11=input.indexOf("11",i+2);
        String acceptingStateString = input.substring(i+2, second11);
        String[] acceptingStateArray = acceptingStateString.split("1");
        for(String s:acceptingStateArray){
            int num = s.length();
            dfsa.addFinalState(num-1);
        }
        
       //get the left string to be checked accept or not.
        String checkString = input.substring(second11+2);
        if(dfsa.isAccepted(checkString)){
            System.out.println("Accepted");
        }
        else{
            System.out.println("Rejected");
        }
        

    }
}
