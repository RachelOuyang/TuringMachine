/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turing1;

/**
 * This is the transition class to store the write symbol, direction, and changed new state.
 * @author ruijieouyang
 */
class Transition {
    public final static char RIGHT = 'R'; 
    public final static char LEFT = 'L'; 
    
    private char toWriteSymbol;
    private char moveDirection;
    private int toStateNum;
    
    /**
     * 
     * @param toWriteSymbol the character to write on the tape of the current position
     * @param moveDirection move pointer to LEFT or RIGHT
     * @param stateNum the sate number to change to
     */
    public Transition(char toWriteSymbol,char moveDirection,int toStateNum){
        this.toWriteSymbol = toWriteSymbol;
        this.moveDirection = moveDirection;
        this.toStateNum = toStateNum;
        
    }
    
    
   /**
     * get the character to write
     * @return the character to write
     * pre-con: the transition is valid
     * post-con: get the character to write
     */
    public char getToWriteSymbol() {
        return toWriteSymbol;
    }
    
    /**
     * get the direction to move the pointer<br>
     * pre-con: the transition is valid<br>
     * post-con: get the direction to move the pointer<br>
     * @return L or R
     */
    public char getMoveDirection() {
        return moveDirection;
    }
    
    /**
     * get the state number that will change to <br>
     * pre-con: the transition is valid<br>
     * post-con: the get the state number to change to<br>
     * running time (all cases): big Theta(1)<br>
     * @return the state number to change to
     */
    public int getToStateNum() {
        return toStateNum;
    }
    
    
    
}
