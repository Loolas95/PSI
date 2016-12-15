/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Karol
 */
public class Reversi {
    
    private  int rows = 8;
    private  int columns = 8;
    private  int userMove = 0;
    private  int computerMove = 0;
    
    public Cell gameCells[][];
    public Cell tmpgameCells[][];
    public Cell tmp1gameCells[][];
    
   
    public Reversi()
    {
        int mid;
        mid = rows / 2;
        gameCells = new Cell[8][];
        tmpgameCells= new Cell[8][];
        tmp1gameCells= new Cell[8][];
        for(int i = 0; i < rows; ++i) {
            gameCells[i] = new Cell[8];
            tmpgameCells[i] = new Cell[8];
            tmp1gameCells[i] = new Cell[8];
        }
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < columns; ++j)
            {   
                gameCells[i][j] = new Cell();
                tmpgameCells[i][j]= new Cell();
                tmp1gameCells[i][j]= new Cell();
                if((i == mid-1) && (j == mid-1)){
                    gameCells[i][j].setCell(j, 'X', i+1);
                }
                else if((i == mid-1) && (j == mid))
                {
                    gameCells[i][j].setCell(j, 'O', i+1);
                }
                else if((i == mid) && (j == mid-1))
                {
                    gameCells[i][j].setCell(j, 'O', i+1);
                }
                else if((i == mid) && (j == mid))
                {
                    gameCells[i][j].setCell(j, 'X', i+1);
                }
                else		
                {
                    gameCells[i][j].setCell(j, '.', i+1);
                }
            }	
        }  
    }
    public Reversi(Reversi obje)
    {
        int y,x;
        char c;
        gameCells = new Cell[8][];
        for(int i = 0; i < rows; ++i)
            gameCells[i] = new Cell[8];
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < columns; ++j)
            {   
                gameCells[i][j] = new Cell();
                
                c = obje.gameCells[i][j].getCh();
                y = obje.gameCells[i][j].getY();
                x = obje.gameCells[i][j].getX();
                gameCells[i][j].setCell(x, c, y);
            }
        }
    }
    public void findMove(ArrayList <Integer> arr)
    {
        int status;
        int change,max = 0; 
        change = 0;
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++)
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    int numberOfMoves[] = new int[1];
                    move(i,j,change,'X','O',numberOfMoves,gameCells);
                    if(numberOfMoves[0] != 0)
                    {
                        arr.add(i);
                        arr.add(j);
                    }    
                } 
            }
        }
    }
public int play(int xCor,int yCor) // play function for user
    {  
        int status;
        int change,max = 0; 
        int numberOfMoves[] = new int[1];
        change = 0;
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    move(i,j,change,'X','O',numberOfMoves,gameCells);
                    if(max < numberOfMoves[0])
                        max = numberOfMoves[0];
                }
            }
        }
        userMove = max;
        if(userMove == 0) // lack of moves
        { 
            userMove = -1;
            return -1;
        }
        else
        {	
            change = 1;
            if(!(gameCells[xCor][yCor].getCh() == '.'))         
                return 1; // innefective move
                
            status = move(xCor,yCor,change,'X','O',numberOfMoves,gameCells);
            if(status == -1)
                return 1; // bad move
        }
        for (int i = 0; i < 8; i++) 
        {
            for (int j = 0; j < 8; j++) {
                System.out.printf("%c",gameCells[i][j].getCh());
            }
            System.out.println("");
        }
        return 0;
    }

 public void countPoints (int arr[] )
    {
        int cross = 0, point = 0, circular = 0;
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                if(gameCells[i][j].getCh() == 'X')
                    cross++;
                else if (gameCells[i][j].getCh() == 'O')
                    circular++;
                else if(gameCells[i][j].getCh() == '.')
                    point++;
            }
        } 
        arr[0] = cross; arr[1] = circular; arr[2] = point;
    }
    public void reset()
    {
        int mid = rows / 2;
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < columns; ++j)
            {   
                char c = (char) (97 + j);
                if((i == mid-1) && (j == mid-1))
                {
                    gameCells[i][j].setCell(c, 'X', i+1);
                }
                else if((i == mid-1) && (j == mid))
                {
                    gameCells[i][j].setCell(c, 'O', i+1);
                }
                else if((i == mid) && (j == mid-1))
                {
                    gameCells[i][j].setCell(c, 'O', i+1);
                }
                else if((i == mid) && (j == mid))
                {
                    gameCells[i][j].setCell(c, 'X', i+1);
                }
                else		
                {
                    gameCells[i][j].setCell(c, '.', i+1);
                }
                System.out.printf("i : %d, j : %d, c : %c\n",i,j,gameCells[i][j].getCh());
            }
        }
    }
    int move();