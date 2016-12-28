/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Karol
 */
public class GUI extends JPanel{

    JPanel panel;
    JPanel boardPanel;
    static JLabel playerscore1;
    static JLabel playerscore2;
    static JButton NewGane;
    static JButton [] cells;
    static Reversi board;
    static ArrayList<Reversi> reversiArrayList = new ArrayList<Reversi>();
    
    static public int playerScore = 2; 
    static public int pcScore = 2;
    private static Reversi start;
    private static int rows = 8;
    private static int cols = 8;
    private static Color col = Color.green;
    
  
    public GUI()
    {
        super(new BorderLayout());    
        setPreferredSize(new Dimension(800,700));
        setLocation(0, 0);
        
        board = new Reversi();
        start = board;
        reversiArrayList.add(new Reversi(board));

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,60));
        
        NewGane = new JButton();
        NewGane.setPreferredSize(new Dimension(80,50));
        try 
        {
            Image img = ImageIO.read(getClass().getResource("images/start.png"));
            NewGane.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
        NewGane.addActionListener(new MainAction());

        JLabel name = new JLabel();
        name.setText("Reversi");
        name.setLocation(750, 680);
        panel.add(NewGane);




        add(panel, BorderLayout.SOUTH);
        
        // Board Panel
        boardPanel = new JPanel(new GridLayout(8,8));
        cells = new JButton[64];

        //FILL A BOARD
        int k=0;
        for(int row = 0; row < rows; row++)
        {
            for(int colum=0; colum < cols; colum++)
            {
                cells[k] = new JButton();
                cells[k].setSize(50, 50);
                cells[k].setBackground(Color.CYAN);
                cells[k].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if(board.gameCells[row][colum].getCh() == 'X')
                {
                    try
                    {
                        Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                        cells[k].setIcon(new ImageIcon(img));
                    } catch (IOException ex) {}
                }
                else if(board.gameCells[row][colum].getCh() == 'O')
                {
                    try
                    {
                        Image img = ImageIO.read(getClass().getResource("images/light.png"));
                        cells[k].setIcon(new ImageIcon(img));
                    } catch (IOException ex) {}
                }
                else if(row == 2 && colum == 4 || row == 3 && colum == 5 ||
                        row == 4 && colum == 2 || row == 5 && colum == 3 )
                {
                    try
                    {
                        Image img = ImageIO.read(getClass().getResource("images/legalMoveIcon.png"));
                        cells[k].setIcon(new ImageIcon(img));
                    } catch (IOException ex) {}
                }
                cells[k].addActionListener(new MainAction());
                boardPanel.add(cells[k]);
                k++;
            }
        }
        add(boardPanel, BorderLayout.CENTER);
        
        
        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        scorePanel.setPreferredSize(new Dimension(210,800));
        
        JLabel dark = new JLabel();
        try 
        {
            Image img = ImageIO.read(getClass().getResource("images/dark.png"));
            dark.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
        JLabel light = new JLabel();
        try 
        {
            Image img = ImageIO.read(getClass().getResource("images/light.png"));
            light.setIcon(new ImageIcon(img));
        } catch (IOException ex) {}
        playerscore1 = new JLabel();
        playerscore1.setText(" Player : " + playerScore + "  ");
        playerscore1.setFont(new Font("Serif", Font.BOLD, 22));
        
        playerscore2 = new JLabel();
        playerscore2.setText(" Computer : " + pcScore + "  ");
        playerscore2.setFont(new Font("Serif", Font.BOLD, 22));
               
        c.gridx = 0;
        c.gridy = 1;
        scorePanel.add(dark, c);  
        c.gridx = 1;
        c.gridy = 1;
        scorePanel.add(playerscore1,c);
        
        
        c.gridx = 0;
        c.gridy = 2;
        scorePanel.add(light, c);  
        c.gridx = 1;
        c.gridy = 2;
        scorePanel.add(playerscore2,c);
              
        add(scorePanel, BorderLayout.EAST);

        
    }

    static class MainAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() == NewGane)
            {
                board.reset();
                reversiArrayList.clear();
                reversiArrayList.add(new Reversi(start));
                int k = 0;
                for (int row = 0; row < rows; row++)
                {
                    for (int colum = 0; colum < cols; colum++)
                    {
                        cells[k].setIcon(null);
                        if(board.gameCells[row][colum].getCh() == 'X')
                        {
                            try
                            {
                                Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                                cells[k].setIcon(new ImageIcon(img));
                            } catch (IOException ex) {}
                        }
                        else if(board.gameCells[row][colum].getCh() == 'O')
                        {
                            try
                            {
                                Image img = ImageIO.read(getClass().getResource("images/light.png"));
                                cells[k].setIcon(new ImageIcon(img));
                            } catch (IOException ex) {}
                        }
                        if(row == 2 && colum == 4 || row == 3 && colum == 5 ||
                        row == 4 && colum == 2 || row == 5 && colum == 3 )
                        {
                            try
                            {
                                Image img = ImageIO.read(getClass().getResource("images/legalMoveIcon.png"));
                                cells[k].setIcon(new ImageIcon(img));
                            } catch (IOException ex) {}
                        }
                        ++k;
                    }
                }
                playerScore = 2; pcScore = 2;
                playerscore1.setText(" Player : " + playerScore + "  ");
                playerscore2.setText(" Computer : " + pcScore + "  ");
            }
            else
            {
                for (int i = 0; i < 64; i++) 
                {
                    if(e.getSource() == cells[i])
                    {
                        int xCor, yCor;
                        int ct ;
                        int arr[] = new int[3];
                        System.out.println("button : "+ i);
                        yCor =i%8;
                        xCor=i/8;
                        ct = board.play(xCor, yCor);
                        if(ct == 0 || ct == -1)
                        {
                            board.play();
                            reversiArrayList.add(new Reversi(board));
                            ArrayList <Integer> arrList = new ArrayList <Integer>();
                            int k=0;
                            for(int row = 0; row < rows; row++) 
                            {
                                for(int colum=0; colum < cols; colum++) 
                                {
                                    if(board.gameCells[row][colum].getCh() == 'X')
                                    {
                                        try 
                                        {
                                            Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                                            cells[k].setIcon(new ImageIcon(img));
                                        } catch (IOException ex) {}
                                    }
                                    else if(board.gameCells[row][colum].getCh() == 'O')
                                    {
                                        try 
                                        {
                                            Image img = ImageIO.read(getClass().getResource("images/light.png"));
                                            cells[k].setIcon(new ImageIcon(img));
                                        } catch (IOException ex) {}                    
                                    }
                                    else if(board.gameCells[row][colum].getCh() == '.')
                                    {
                                        cells[k].setIcon(null);
                                    }
                                    k++;
                                }
                            }
                            board.findMove(arrList);
                            for (int j = 0; j < arrList.size(); j += 2) 
                            {
                                try 
                                {
                                    Image img = ImageIO.read(getClass().getResource("images/legalMoveIcon.png"));
                                    cells[arrList.get(j)*rows + arrList.get(j + 1)].setIcon(new ImageIcon(img));
                                } catch (IOException ex) {}  
                            }
                            board.countPoints(arr);
                            playerScore = arr[0]; pcScore = arr[1];
                            playerscore1.setText("Player : " + playerScore + "  ");
                            playerscore2.setText("Computer : " + pcScore + "  ");
                        }
                    }

                }
                int st = board.endGame();
                if(st == 0)
                {
                    if(playerScore > pcScore)
                        JOptionPane.showMessageDialog(null,"No legal move!\nPlayer Win!","Game Over",JOptionPane.PLAIN_MESSAGE);   
                    else
                        JOptionPane.showMessageDialog(null,"No legal move!\nComputer Win!","Game Over",JOptionPane.PLAIN_MESSAGE);
                }
                else if(st == 1 || st == 3)
                {
                    JOptionPane.showMessageDialog(null,"Computer Win!","Game Over",JOptionPane.PLAIN_MESSAGE);
                }
                else if(st == 2 || st == 4)
                {
                    JOptionPane.showMessageDialog(null,"Player Win!","Game Over",JOptionPane.PLAIN_MESSAGE); 
                }
                else if(st == 5)
                {
                    JOptionPane.showMessageDialog(null,"Scoreless!","Game Over",JOptionPane.PLAIN_MESSAGE); 
                }
            }
        }
        
    }
    
}
