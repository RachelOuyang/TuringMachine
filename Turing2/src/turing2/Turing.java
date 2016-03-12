/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing2;

import java.util.LinkedList;

/**
 * Precondition: 
 * Our tape will be infinite in both directions. To simulate this, define an 
 * array of size 40 and set the initial read/write head to 20 (the middle of the
 * tape). Our simulation will only work for machines that stay within this range. 
 * So the test input string should be less than 20 length.
 *
 * @author ruijieouyang
 */
class Turing {

    public final static int TAPE_LENGTH = 40;
    public final static int INITIAL_HEAD = 20;

    private char[] tape;
    private int totalStates;
    private int startState;
    private int currentState;
    private LinkedList<Object[]>[] delta;

    public Turing(int n) {
        //there is an additional halt state
        this.totalStates = n + 1;

        //initliaze delta
        delta = new LinkedList[totalStates];
        for (int i = 0; i < delta.length; i++) {
            delta[i] = new LinkedList();
        }

        //initialize the outputTape with Blank at first
        tape = new char[TAPE_LENGTH];
        tape = new char[TAPE_LENGTH];
        for (int i = 0; i < tape.length; i++) {
            tape[i] = 'B';
           
        }

        //set start state
        startState = 0;

    }

    /**
     * add transition rules into delta set
     *
     * @param fromState the current state
     * @param seeSymbol the character the Turing machine read from the tape
     * @param tran the transition to do pre-condition: the from State is not the
     * halt state, and the seeChar is valid<br>
     * post-condition: add the transition rules into the delta set<br>
     */
    public void addTransition(int fromState, char seeSymbol, Transition trans) {
        Object[] transInfo = new Object[4];
        transInfo[0] = seeSymbol;
        transInfo[1] = trans.getToStateNum();
        transInfo[2] = trans.getToWriteSymbol();
        transInfo[3] = trans.getMoveDirection();
        delta[fromState].addLast(transInfo);

    }

    /**
     * according to the character that Turing machine see on the tape, it will
     * execute by the transition rules in delta
     *
     * @param inputTape the tape that the Turing machine to read
     * @return the replaced tape
     */
    public String execute(String input) {
        changeInputTape(input);
        currentState = startState;
        int head = INITIAL_HEAD - 1;

        //while haven't reach halt state q6
        while (currentState != totalStates-1) {
            int i = 0;
            Object[] transInfo = delta[currentState].get(i);
           // System.out.println("current state from: "+currentState);

            while (transInfo != null) {
                char seeSymbol = (char) transInfo[0];
                int toStateNum = (int) transInfo[1];
                char toWriteSymbol = (char) transInfo[2];
                char moveDirection = (char) transInfo[3];

                //find the object[] element in the linkedlist that
                //object[0](seeSymbol) is equal to the char read from inputTape.
                //else go to next element and check if find.
                if (tape[head] == seeSymbol) {
                    currentState = toStateNum;//currentState == toStateNum                 
                    tape[head] = toWriteSymbol;
                    if (moveDirection == 'R') {
                        
                        head++;
                    } else if (moveDirection == 'L') {
                        
                        head--;
                    }

                    break;
                }

                //transInfo = next transInfo
                i++;
                if(i>=delta[currentState].size()){
                    break;
                }
                transInfo = delta[currentState].get(i);
            }
        }

        return new String(tape);
    }
    /**
     * put the input string into the tape.start at position 20 on the tape. 
     * @param input 
     */
    private void changeInputTape(String input) {
        //initialize the input tape array
        int head = INITIAL_HEAD - 1;
        for(int i=0;i<input.length();i++){
            tape[head] = input.charAt(i);
            head++;
        }

    }

}
