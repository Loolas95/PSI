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
    public int play() // play function for computer
    {  
        int change,max = 0,mX = 0,mY = 0;
        change = 0;
        int mincounter;
        int numberOfMoves[] = new int[1];
        //STRATEGY
        //0-normal
        //1-corners
        //2-keeping the corners out
        //3-stable disc
        //4-min_max algorithm
        int option=4;



        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                if(gameCells[i][j].getCh() == '.') {
                    if (option == 4) {
                        copyArray();
                        mincounter=0;
                        change=1;
                        move2(i, j, change, 'O', 'X', numberOfMoves, tmpgameCells);
                        mincounter+=numberOfMoves[0];

                        if(numberOfMoves[0]>0) {
                           ArrayList list=minmaxplayerplay();
                            for(int count=0;count<list.size();count++){
                                int counter1=mincounter;
                                counter1+=list.indexOf(i);
                                if(max<counter1){
                                    max=counter1;
                                    mX=i;
                                    mY=j;
                                }

                            }
                        }

                    } else {







                        move(i, j, change, 'O', 'X', numberOfMoves, gameCells);
                        if (max <= numberOfMoves[0]) {
                            if (max != 0 && option == 2 && nearcorner(i, j)) {
                            } else {
                                max = numberOfMoves[0];
                                mX = i;
                                mY = j;
                            }
                        }
                        if (option == 1) {
                            if ((i == 0 && j == 0 || i == rows && j == 0 || i == 0 && j == columns || i == rows && j == columns) && option == 1 && numberOfMoves[0] > 0) {
                                max = 100;
                                mX = i;
                                mY = j;
                            }
                        }
                        if (option == 3) {
                            if (i + j < 8 && option == 3 && numberOfMoves[0] > 0) {
                                max = 100;
                                mX = i;
                                mY = j;
                            }
                        }
                    }
                }
            }
        }	
        computerMove = max;
        if (computerMove == 0)
        {
            computerMove = -1;
            return -1;
        }
        if(computerMove != 0)
        {
            change = 1;
            move(mX,mY,change,'O','X',numberOfMoves,gameCells);
        }
        return 0;
    }
    public int minmaxcompplay() // minmaxplay function for computer
    {
        int change,max = 0,mX = 0,mY = 0;
        change = 0;
        int numberOfMoves[] = new int[1];


        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                if(tmpgameCells[i][j].getCh() == '.') {
                        move2(i, j, change, 'O', 'X', numberOfMoves, tmpgameCells);
                        if (max <= numberOfMoves[0]) {
                                max = numberOfMoves[0];
                                mX = i;
                                mY = j;
                            }
                }
            }
        }
        computerMove = max;
        if (computerMove == 0)
        {
            computerMove = -1;
            return computerMove;
        }
        if(computerMove != 0)
        {
            change = 1;
            move2(mX,mY,change,'O','X',numberOfMoves,tmpgameCells);
        }
        return computerMove;
    }
    public ArrayList<Integer> minmaxplayerplay() // minmaxplay function for user
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int change,max = 0;
        int numberOfMoves[] = new int[1];
        change = 0;
        copyArray1();
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
            {
                if(tmpgameCells[i][j].getCh() == '.')
                {
                   copytmp1Totmp();
                    change=1;
                    move2(i,j,change,'X','O',numberOfMoves,tmpgameCells);
                    if(numberOfMoves[0]>0) {
                        max = numberOfMoves[0];
                        max+=minmaxcompplay();
                        list.add(max);
                    }
                }
            }
        }
      return list;
    }
    private void copyArray() {
        for(int i = 0; i< rows; i++){
            for (int j = 0; j < columns; j++){
                int x = gameCells[i][j].getX();
                char c = gameCells[i][j].getCh();
                int y = gameCells[i][j].getY();
                tmpgameCells[i][j].setCell(x,c,y);

            }
        }
    }
    private void copyArray1() {
        for(int i = 0; i< rows; i++){
            for (int j = 0; j < columns; j++){
                int x = tmpgameCells[i][j].getX();
                char c = tmpgameCells[i][j].getCh();
                int y = tmpgameCells[i][j].getY();
                tmp1gameCells[i][j].setCell(x,c,y);

            }
        }
    }
    private void copytmp1Totmp() {
        for(int i = 0; i< rows; i++){
            for (int j = 0; j < columns; j++){
                int x = tmp1gameCells[i][j].getX();
                char c = tmp1gameCells[i][j].getCh();
                int y = tmp1gameCells[i][j].getY();
                tmpgameCells[i][j].setCell(x,c,y);

            }
        }
    }


    public boolean nearcorner(int i, int j){
        if ((i==0 && j==1)||(i==1 && j==1)||(i==1 && j==0)) return true;
        if ((i==rows-2 && j==0)||(i==rows-2 && j==1)||(i==rows-1 && j==1)) return true;
        if ((i== columns -2 && j==0)||(i== columns -2 && j==1)||(i== columns -1 && j==0)) return true;
        if ((i==rows-1 && j== columns -1)||(i==rows-2 && j== columns -1)||(i==rows-1 && j== columns -2)) return true;
        return false;

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

    public int endGame()
    {
        int[] arr = new int[3];
        int cross, circular, point ;
        countPoints(arr);
        cross = arr[0];
        circular = arr[1];
        point = arr[2];
        
        if( (userMove == -1 && computerMove == -1) || point == 0)
        {
            if(userMove == -1 && computerMove == -1) //No legal move
                return 0;
            if(circular > cross)
                return 1;
            else if(cross > circular)
                return 2;
            else if(cross == 0)
                return 3;
            else if(circular == 0)
                return 4;
            else // scoreless
              return 5;
        }
        return -1;
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
    int move(int xCor, int yCor,int change,char char1,char char2,int []numberOfMoves, Cell [][] gameCells)
    {
	int cont,emptyfield=0,color=0;
	int status = -1,corX,corY,temp;
        int iy;
        int ix,y,x;
        
        x = xCor; y = yCor;
	numberOfMoves[0] = 0;
	if((x+1 < rows) && ( gameCells[x+1][y].getCh() == char2)) //east
	{	

            cont = x;
            while((cont < rows)  && (emptyfield != -1) && (color != 2))
            {
                cont++;
                if(cont < rows){
                    if(gameCells[cont][y].getCh() == char2)
                        color = 1;
                    else if(gameCells[cont][y].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = cont - x - 1;
                numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                for (int i = x; i < cont; ++i)
                {
                    iy = gameCells[i][y].getX();
                    ix = gameCells[i][y].getY();
                    gameCells[i][y].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;
	}
	if((x-1 >= 0) && (gameCells[x-1][y].getCh() == char2)) //west
	{

            cont = x;
            while((cont >= 0) && (emptyfield != -1) && (color != 2))
            {
                cont--;
                if(cont >= 0){
                    if(gameCells[cont][y].getCh() == char2)
                        color = 1;
                    else if(gameCells[cont][y].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }	
            if (color == 2)
            {
                temp = x - cont - 1;
                numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                for (int i = cont; i <= x; ++i)
                {
                    iy = gameCells[i][y].getX();
                    ix = gameCells[i][y].getY();
                    gameCells[i][y].setCell(iy,char1,ix);
                }
                status = 0;
            }		
            color=0;emptyfield=0;
	}
	if((y+1 < columns) && (gameCells[x][y+1].getCh() == char2)) //south
	{

            cont = y;
            while((cont < columns) && (emptyfield != -1) && (color != 2))
            {
                cont++;
                if(cont < columns){
                    if(gameCells[x][cont].getCh() == char2)
                        color = 1;
                    else if(gameCells[x][cont].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }	
            }	
            if (color == 2)
            {
                    temp = cont - y - 1;
                    numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                for (int i = y; i < cont; ++i)
                {
                    iy = gameCells[x][i].getX();
                    ix = gameCells[x][i].getY();
                    gameCells[x][i].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;emptyfield=0;
	}
	if((y-1 >= 0) && (gameCells[x][y-1].getCh() == char2)) //north
	{

            cont = y;
            while((cont >= 0) && (emptyfield != -1) && (color != 2))
            {
                cont--;
                if(cont >= 0){
                    if(gameCells[x][cont].getCh() == char2)
                        color = 1;
                    else if(gameCells[x][cont].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }		
            }	
            if (color == 2)
            {
                    temp = y - cont - 1;
                    numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                for (int i = cont; i <= y; ++i)
                {
                    iy = gameCells[x][i].getX();
                    ix = gameCells[x][i].getY();
                    gameCells[x][i].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;emptyfield=0;
	}
	if((x-1 >= 0) && (y+1 < columns) && (gameCells[x-1][y+1].getCh() == char2)) //SE
	{

            corY = y;
            corX = x;
            while((corX >= 0) && (corY < columns) && (emptyfield != -1) && (color != 2))
            {
                corX--;
                corY++;
                if((corX >= 0) && (corY < columns)){
                    if(gameCells[corX][corY].getCh() == char2)
                        color = 1;
                    else if(gameCells[corX][corY].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }			
            }	
            if (color == 2)
            {
                    temp = x - corX - 1;
                    numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                while((corX <= x) && (y < corY))
                {
                    corX++;
                    corY--;

                    if((corX <= x) && (y <= corY)){
                        iy = gameCells[corX][corY].getX();
                        ix = gameCells[corX][corY].getY();
                        gameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }
            color=0;emptyfield=0;
	}
	if((x-1 >= 0) && (y-1 >= 0) && (gameCells[x-1][y-1].getCh() == char2)) //NW
	{
            corY = y;
            corX = x;
            while((corX >= 0) && (corY >= 0) && (emptyfield != -1) && (color != 2))
            {
                corX--;
                corY--;
                if((corX >= 0) && (corY >= 0)){
                    if(gameCells[corX][corY].getCh() == char2)
                        color = 1;
                    else if(gameCells[corX][corY].getCh() == char1)
                        color = 2;
                    else 
                        emptyfield = -1;
                }		
            }	
            if (color == 2)
            {
                    temp = x - corX - 1;
                    numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                while((corX <= x) && (corY <= y))
                {
                    corX++;
                    corY++;
                    if((corX <= x) && (corY <= y)){
                        iy = gameCells[corX][corY].getX();
                        ix = gameCells[corX][corY].getY();
                        gameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }
            color=0;emptyfield=0;
	}
	if((x+1 < rows) && (y+1 < columns) && (gameCells[x+1][y+1].getCh() == char2)) //SW
	{

            corY = y;
            corX = x;
            while((corX < rows) && (corY < columns) && (emptyfield != -1) && (color != 2))
            {
                corX++;
                corY++;
                if((corX < rows) && (corY < columns)){
                    if(gameCells[corX][corY].getCh() == char2)
                            color = 1;
                    else if(gameCells[corX][corY].getCh() == char1)
                            color = 2;
                    else 
                            emptyfield = -1;
                }	
            }	
            if (color == 2)
            {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                while((corX >= x) && (corY >= y))
                {
                    corX--;
                    corY--;
                    if((corX >= x) && (corY >= y)){
                        iy = gameCells[corX][corY].getX();
                        ix = gameCells[corX][corY].getY();
                        gameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }
            color=0;emptyfield=0;
	}
	if((x+1 < rows) && (y-1 >= 0) && (gameCells[x+1][y-1].getCh() == char2)) //NE
	{

            corY = y;
            corX = x;
            while((corX < rows) && (corY >= 0) && (emptyfield != -1) && (color != 2))
            {
                    corX++;
                    corY--;
                    if((corX < rows) && (corY >= 0)){
                        if(gameCells[corX][corY].getCh() == char2)
                            color = 1;
                        else if(gameCells[corX][corY].getCh() == char1)
                            color = 2;
                        else 
                            emptyfield = -1;
                   }		
            }	
            if (color == 2)
            {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }	
            if(color == 2 && change == 1)
            {
                while((corX >= x) && (corY <= y))
                {
                    corX--;
                    corY++;
                    if((corX >= x) && (corY <= y)){
                        iy = gameCells[corX][corY].getX();
                        ix = gameCells[corX][corY].getY();
                        gameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }

	}
	if(status == 0)
            return 0;
	else
            return -1;			
    }
    int move2(int xCor, int yCor,int change,char char1,char char2,int []numberOfMoves, Cell [][] tmpgameCells)
    {
        int cont,emptyfield=0,color=0;
        int status = -1,corX,corY,temp;
        int iy;
        int ix,y,x;

        x = xCor; y = yCor;
        numberOfMoves[0] = 0;
        if((x+1 < rows) && ( tmpgameCells[x+1][y].getCh() == char2)) //east
        {

            cont = x;
            while((cont < rows)  && (emptyfield != -1) && (color != 2))
            {
                cont++;
                if(cont < rows){
                    if(tmpgameCells[cont][y].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[cont][y].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = cont - x - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                for (int i = x; i < cont; ++i)
                {
                    iy = tmpgameCells[i][y].getX();
                    ix = tmpgameCells[i][y].getY();
                    tmpgameCells[i][y].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;
        }
        if((x-1 >= 0) && (tmpgameCells[x-1][y].getCh() == char2)) //west
        {

            cont = x;
            while((cont >= 0) && (emptyfield != -1) && (color != 2))
            {
                cont--;
                if(cont >= 0){
                    if(tmpgameCells[cont][y].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[cont][y].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = x - cont - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                for (int i = cont; i <= x; ++i)
                {
                    iy = tmpgameCells[i][y].getX();
                    ix = tmpgameCells[i][y].getY();
                    tmpgameCells[i][y].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;emptyfield=0;
        }
        if((y+1 < columns) && (tmpgameCells[x][y+1].getCh() == char2)) //south
        {

            cont = y;
            while((cont < columns) && (emptyfield != -1) && (color != 2))
            {
                cont++;
                if(cont < columns){
                    if(tmpgameCells[x][cont].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[x][cont].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = cont - y - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                for (int i = y; i < cont; ++i)
                {
                    iy = tmpgameCells[x][i].getX();
                    ix = tmpgameCells[x][i].getY();
                    tmpgameCells[x][i].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;emptyfield=0;
        }
        if((y-1 >= 0) && (tmpgameCells[x][y-1].getCh() == char2)) //north
        {

            cont = y;
            while((cont >= 0) && (emptyfield != -1) && (color != 2))
            {
                cont--;
                if(cont >= 0){
                    if(tmpgameCells[x][cont].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[x][cont].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = y - cont - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                for (int i = cont; i <= y; ++i)
                {
                    iy = tmpgameCells[x][i].getX();
                    ix = tmpgameCells[x][i].getY();
                    tmpgameCells[x][i].setCell(iy,char1,ix);
                }
                status = 0;
            }
            color=0;emptyfield=0;
        }
        if((x-1 >= 0) && (y+1 < columns) && (tmpgameCells[x-1][y+1].getCh() == char2)) //SE
        {

            corY = y;
            corX = x;
            while((corX >= 0) && (corY < columns) && (emptyfield != -1) && (color != 2))
            {
                corX--;
                corY++;
                if((corX >= 0) && (corY < columns)){
                    if(tmpgameCells[corX][corY].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[corX][corY].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = x - corX - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                while((corX <= x) && (y < corY))
                {
                    corX++;
                    corY--;

                    if((corX <= x) && (y <= corY)){
                        iy = tmpgameCells[corX][corY].getX();
                        ix = tmpgameCells[corX][corY].getY();
                        tmpgameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }
            color=0;emptyfield=0;
        }
        if((x-1 >= 0) && (y-1 >= 0) && (tmpgameCells[x-1][y-1].getCh() == char2)) //NW
        {
            corY = y;
            corX = x;
            while((corX >= 0) && (corY >= 0) && (emptyfield != -1) && (color != 2))
            {
                corX--;
                corY--;
                if((corX >= 0) && (corY >= 0)){
                    if(tmpgameCells[corX][corY].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[corX][corY].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = x - corX - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                while((corX <= x) && (corY <= y))
                {
                    corX++;
                    corY++;
                    if((corX <= x) && (corY <= y)){
                        iy = tmpgameCells[corX][corY].getX();
                        ix = tmpgameCells[corX][corY].getY();
                        tmpgameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }
            color=0;emptyfield=0;
        }
        if((x+1 < rows) && (y+1 < columns) && (tmpgameCells[x+1][y+1].getCh() == char2)) //SW
        {

            corY = y;
            corX = x;
            while((corX < rows) && (corY < columns) && (emptyfield != -1) && (color != 2))
            {
                corX++;
                corY++;
                if((corX < rows) && (corY < columns)){
                    if(tmpgameCells[corX][corY].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[corX][corY].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                while((corX >= x) && (corY >= y))
                {
                    corX--;
                    corY--;
                    if((corX >= x) && (corY >= y)){
                        iy = tmpgameCells[corX][corY].getX();
                        ix = tmpgameCells[corX][corY].getY();
                        tmpgameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }
            color=0;emptyfield=0;
        }
        if((x+1 < rows) && (y-1 >= 0) && (tmpgameCells[x+1][y-1].getCh() == char2)) //NE
        {

            corY = y;
            corX = x;
            while((corX < rows) && (corY >= 0) && (emptyfield != -1) && (color != 2))
            {
                corX++;
                corY--;
                if((corX < rows) && (corY >= 0)){
                    if(tmpgameCells[corX][corY].getCh() == char2)
                        color = 1;
                    else if(tmpgameCells[corX][corY].getCh() == char1)
                        color = 2;
                    else
                        emptyfield = -1;
                }
            }
            if (color == 2)
            {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }
            if(color == 2 && change == 1)
            {
                while((corX >= x) && (corY <= y))
                {
                    corX--;
                    corY++;
                    if((corX >= x) && (corY <= y)){
                        iy = tmpgameCells[corX][corY].getX();
                        ix = tmpgameCells[corX][corY].getY();
                        tmpgameCells[corX][corY].setCell(iy,char1,ix);
                    }
                }
                status = 0;
            }

        }
        if(status == 0)
            return 0;
        else
            return -1;
    }
}
