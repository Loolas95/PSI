/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

/**
 *
 * @author Karol
 */
public class Cell 
{
    
    private int Y;
    private int X;
    private char ch;
    public Cell(int x, int y, char c)
    {
        Y = y;
        X = x;
        ch = c;  
    }

    Cell() {}
    int getX()
    {
        return X;
    }
    int getY()
    { 
        return Y;
    }
    char getCh() 
    {
        return ch; 
    }
    void setCell(int x, char c, int y)
    {
        X = x;
        Y = y;
        ch = c;
    }
}
