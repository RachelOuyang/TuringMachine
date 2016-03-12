/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing1;

/**
 * This a turing machine simulator program. 
 * Precondition: 
 * Our tape will be infinite in both directions. To simulate this, define an 
 * array of size 40 and set the initial read/write head to 20 (the middle of the
 * tape). Our simulation will only work for machines that stay within this range. 
 * So the test input string should be less than 20 length.
 *
 * This machine reads the tape from left to right and replaces any 1's with 0's
 * and any 0's with 1's. It stops, by entering the halt state, when it
 * encounters a B in the input. We will call this a one state machine
 *
 * @author ruijieouyang
 */
public class Turing1 {

    /**
     * @param args the command line arguments
     * Precondition:
     * inTape should be less than 20 char length, should be in correct format
     * that contains only 0 and 1 char, not include 'B' or other character.
     */
    public static void main(String[] args) {
        Turing machine = new Turing(1);  // This machine will have one state.
        // Note: There is an additional halt state.
        // The values on the input tape are set to 
        //  all B's.

        Transition one = new Transition('0', Transition.RIGHT, 0);
        Transition two = new Transition('1', Transition.RIGHT, 0);
        Transition three = new Transition('B', Transition.LEFT, 1);

        machine.addTransition(0, '0', two);//(q0,0) = (q0,1,R)    
        machine.addTransition(0, '1', one);//(q0,1) = (q0,0,R)    
        machine.addTransition(0, 'B', three);//(q0,B) = (q1,B,L)    

        String inTape = "11111100010101";   // The leftmost value of inTape will be 
        // placed under the read/write head.

        System.out.println(inTape);

        String outTape = machine.execute(inTape);
        System.out.println(outTape);

    }

}
