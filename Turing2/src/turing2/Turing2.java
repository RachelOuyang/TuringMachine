/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing2;

/**
 * This a turing machine simulator program. <br>
 * Precondition: <br>
 * Our tape will be infinite in both directions. To simulate this, define an
 * array of size 40 and set the initial read/write head to 20 (the middle of the
 * tape). Our simulation will only work for machines that stay within this
 * range. So the test input string should be less than 20 length.<br>
 *
 * it simulates the following machine:<br>
 * Proper subtraction m – n is defined to be m – n for m >= n, and zero for m
 * <n. The TM
 *
 * M = ( {q0,q1,...,q6}, {0,1}, {0,1,B}, , q0, B, {} )<br>
 *
 * defined below, if started with 0m10n on its tape, halts with 0m-n on its
 * tape. M repeatedly replaces its leading 0 by blank, then searches right for a
 * 1 followed by a 0 and changes the 0 to a 1. Next, M moves left until it
 * encounters a blank and then repeats the cycle. The repetition ends if *
 * Searching right for a 0, M encounters a blank. Then, the n 0’s in 0m10n have
 * all been changed to 1’s, and n+1 of the m 0’s have been changed to B. M
 * replaces the n+1 1’s by a 0 and n B’s, leaving m-n 0’s on its tape. Beginning
 * the cycle, M cannot find a 0 to change to a blank, because the first m 0’s
 * already have been changed. Then n >= m, so m – n = 0. M replaces all remaing
 * 1’s and 0’s by B.
 *
 *
 * @author ruijieouyang
 */
public class Turing2 {

    /**
     * @param args the command line arguments Precondition: inTape should be
     * less than 20 char length, should be in correct format that contains only
     * 0 and 1 char, not include 'B' or other character.
     */
    public static void main(String[] args) {
        Turing machine = new Turing(6);  // This machine will have 6 state.
        // Note: There is an additional halt state.
        // The values on the input tape are set to 
        //  all B's.

        //Transition(char toWriteSymbol,char moveDirection,int toStateNum){
        Transition one = new Transition('B', Transition.RIGHT, 1);

        Transition two = new Transition('0', Transition.RIGHT, 1);
        Transition three = new Transition('1', Transition.RIGHT, 2);

        Transition four = new Transition('1', Transition.LEFT, 3);
        Transition five = new Transition('0', Transition.LEFT, 3);

        Transition six = new Transition('B', Transition.RIGHT, 0);

        Transition seven = new Transition('B', Transition.LEFT, 4);
        Transition eight = new Transition('0', Transition.LEFT, 4);
        Transition nine = new Transition('0', Transition.RIGHT, 6);

        Transition ten = new Transition('B', Transition.RIGHT, 5);
        Transition eleven = new Transition('B', Transition.RIGHT, 6);

        //addTransition(int fromState, char seeSymbol, Transition trans)
        machine.addTransition(0, '0', one);//(q0,0) = (q1,B,R)   Begin. Replace the leading 0 by B.

        machine.addTransition(1, '0', two);//(q1,0) = (q1,0,R)   Search right looking for the first 1.
        machine.addTransition(1, '1', three);//(q1,1) = (q2,1,R)

        machine.addTransition(2, '1', three);//(q2,1) = (q2,1,R)   Search right past 1’s until encountering a 0. Change that 0 to 1.
        machine.addTransition(2, '0', four);//(q2,0) = (q3,1,L)

        machine.addTransition(3, '0', five);//(q3,0) = (q3,0,L)   Move left to a blank. Enter state q0 to repeat the cycle.
        machine.addTransition(3, '1', four);//(q3,1) = (q3,1,L)   
        machine.addTransition(3, 'B', six);//(q3,B) = (q0,B,R)

        /**
         * If in state q2 a B is encountered before a 0, we have situation i
         * described above. Enter state q4 and move left, changing all 1’s to
         * B’s until encountering a B. This B is changed back to a 0, state q6
         * is entered and M halts.
         *
         */
        machine.addTransition(2, 'B', seven);//(q2,B) = (q4,B,L)   
        machine.addTransition(4, '1', seven);//(q4,1) = (q4,B,L)
        machine.addTransition(4, '0', eight);//(q4,0) = (q4,0,L)
        machine.addTransition(4, 'B', nine);//(q4,B) = (q6,0,R)

        /**
         * If in state q0 a 1 is encountered instead of a 0, the first block of
         * 0’s has been exhausted, as in situation (ii) above. M enters state q5
         * to erase the rest of the tape, then enters q6 and halts.          *
         */
        machine.addTransition(0, '1', ten);//(q0,1) = (q5,B,R)   
        machine.addTransition(5, '0', ten);//(q5,0) = (q5,B,R)
        machine.addTransition(5, '1', ten);//(q5,1) = (q5,B,R)
        machine.addTransition(5, 'B', eleven);//(q5,B) = (q6,B,R)

        String inTape = "000100";   // 0^2 1 0^1,m=2,n=1,halt with 0^(2-1) 

        System.out.println(inTape);

        String outTape = machine.execute(inTape);
        System.out.println(outTape);
    }

}
