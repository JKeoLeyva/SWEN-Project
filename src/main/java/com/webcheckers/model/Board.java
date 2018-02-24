package com.webcheckers.model;

import java.util.ArrayList;

/**
 * @author sam davis sjd8078
 */
public class Board {

    private Player redPlayer;
    private Player whitePlayer;
    private Player currPlayer;
    private ArrayList<ArrayList<String>> board;
    private final int BOARD_SIZE = 8;

    public static enum Piece{
        RED, WHITE, RED_KING, WHITE_KING, EMPTY
    }

    public Board(Player redPlayer, Player whitePlayer){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currPlayer = redPlayer;

        this.board = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            board.add(new ArrayList<>());
        }

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                //red half of board
                if(i < BOARD_SIZE/2){

                }
                //white half of the board
                else{

                }
            }
        }
    }

    public Player getRedPlayer(){
        return redPlayer;
    }

    public Player getWhitePlayer(){
        return whitePlayer;
    }

    public Player getCurrPlayer(){
        return currPlayer;
    }

}
