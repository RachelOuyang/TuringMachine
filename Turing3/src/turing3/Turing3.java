/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing3;

/**
 * a Turing machine that reads a series of one’s and makes a copy of those one’s
 * to the same tape.  *
 * There will be a single B separating the two lists on the final output. For
 * example, if the input is 11111 then the output will be B11111B11111B. If the
 * input is 1 then the output will be B1B1B. If the input is the empty string
 * then the output will be a blank tape (all B’s). If the input is 1111111111
 * then the output will be B1111111111B11111111B. We are using the ‘B’ symbol to
 * represent a blank tape symbol. So, we are assuming that there are an infinity
 * of B’s on each side of the input. Do not worry about the number of blanks
 * appearing before or after the input. Don’t worry about the number of B’s
 * appearing before or after your string on output. The only blank we care about
 * is the one between the two strings of 1’s. You may assume that the read head
 * is positioned on top of the first 1 in the input (or, on a blank if the input
 * is empty.)
 *
 * @author ruijieouyang
 */
public class Turing3 {

    /**
     * @param args the command line arguments
     * Assume that users input are correct format that only contains 1 and less
     * than 20 character length.
     */
    public static void main(String[] args) {
        Turing machine = new Turing(4);  // This machine will have 6 state.
        // Note: There is an additional halt state.
        // The values on the input tape are set to 
        //  all B's.

        //Transition(char toWriteSymbol,char moveDirection,int toStateNum){
        Transition one = new Transition('$', Transition.RIGHT, 0);
        Transition two = new Transition('0', Transition.LEFT, 1);

        Transition three = new Transition('1', Transition.RIGHT, 2);
        Transition four = new Transition('1', Transition.LEFT, 1);

        Transition five = new Transition('0', Transition.RIGHT, 2);
        Transition six = new Transition('1', Transition.RIGHT, 2);

        Transition seven = new Transition('1', Transition.LEFT, 3);
        Transition eight = new Transition('B', Transition.RIGHT, 4);

        //addTransition(int fromState, char seeSymbol, Transition trans)
        machine.addTransition(0, '1', one);//(q0,1)->(q0,$,R), start and replace every 1 with $
        machine.addTransition(0, 'B', two);//(q0,B)->(q1,0,L),when encounter the 1st blank, change to 0 and move left      

        //repeat
        //find the rightmost $, replace it with 1
        machine.addTransition(1, '$', three);//(q1,$)->(q2,1,R)
        machine.addTransition(1, '1', four);//(q1,1)->(q1,1,L)

        //go the right end,escpage the middle 0 and 1's that are in the right of 0,that is go to the right end.
        machine.addTransition(2, '0', five);//(q2,0)->(q2,0,R)
        machine.addTransition(2, '1', six);//(q2,1)->(q2,1,R)
        machine.addTransition(2, 'B', seven);//(q2,B)->(q3,1,L) //when at right end B, insert 1

        //then keep moving right to left of the middle 0
        machine.addTransition(3, '1', seven);//(q3,1)->(q3,1,L)
        machine.addTransition(3, '0', two);//(q3,0)->(q1,0,L)

        //reapeat end when there is no more $ remain, halt.
        machine.addTransition(1, 'B', eight);//(q1,B)->(q4,B,R)

        String inTape = "1";

        System.out.println(inTape);

        String outTape = machine.execute(inTape);
        System.out.println(outTape);
    }

}
