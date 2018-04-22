package model;//import gameinterface.Location;

import gameinterface.InvalidRangeException;
import gameinterface.Location;
import gameinterface.MineSweeperModel;

import java.util.Scanner;

public class MyMinesweeper implements MineSweeperModel
{
    private Board gameBoard;
    private final boolean inProcess = true;
    private boolean gameState;
    private boolean first;
    private boolean lost;
    private int numOfClick;

    public MyMinesweeper(int height, int width, int numOfMines) throws InvalidRangeException
    {
        if(height<=0 || width<=0 || numOfMines<=0 || numOfMines>height*width)
            throw new InvalidRangeException();
        gameBoard = new Board(height, width, numOfMines);
        gameState = true;
        first = true;
        lost = false;
        numOfClick = 0;
    }

    public MyMinesweeper(int height, int width) throws InvalidRangeException
    {
        if(height<=0 || width<=0)
            throw new InvalidRangeException();
        else
        {
            gameBoard = new Board(height, width);
            gameState = true;
            first = true;
           // play();
        }
    }

    public int getHeight()
    {
        return gameBoard.getHeight();
    }

    @Override
    public String getValueAt(Location location)
    {
        if(location.getRow()<gameBoard.getHeight() && location.getColumn()<gameBoard.getWidth()
                && location.getRow()>=0 && location.getColumn()>=0)
        {
            if(gameBoard.getCell(location.getRow(), location.getColumn()).isFlagged())
                return "F";
            else
                return "O";
        }
        return null;
    }

    @Override
    public void checkLocation(Location location) {
        if(location.getRow()<gameBoard.getHeight() && location.getColumn()<gameBoard.getWidth()
                && location.getRow()>=0 && location.getColumn()>=0)
        this.click(location.getRow()+1, location.getColumn()+1);
    }

    @Override
    public void flagLocation(Location location) {
        if(location.getRow()<gameBoard.getHeight() && location.getColumn()<gameBoard.getWidth()
                && location.getRow()>=0 && location.getColumn()>=0)
        this.flag(location.getRow()+1, location.getColumn()+1);
    }

    @Override
    public int getNrOfActions() {
        return numOfClick;
    }

    @Override
    public long getNrOfMinesLeft() {
        return gameBoard.getNumOfLeftMines();
    }

    @Override
    public boolean getLost() {
        return lost;
    }

    public int getWidth()
    {
        return gameBoard.getWidth();
    }

    public void play()
    {
        while(gameState==inProcess)
        {
            gameBoard.display();
            nextProcess();
        }
        gameBoard.overview();
    }

    public void nextProcess()
    {
        Scanner sc = new Scanner(System.in);
        String[] readLine = sc.nextLine().split(" ");
        try
        {
            if(readLine[0].equals("help"))
            {
                this.help();
            }
            if(readLine[0].equals("quit"))
            {
                this.setGameState(false);
            }
            if(readLine[0].equals("exit"))
            {
                this.setGameState(false);
            }
            if(readLine[0].equals("F") && this.isInt(readLine[1]) && this.isInt(readLine[2]))
            {
                int x = Integer.parseInt(readLine[1]);
                int y = Integer.parseInt(readLine[2]);
                this.flag(x,y);
            }
            if(this.isInt(readLine[0]) && this.isInt(readLine[1]) )
            {
                int x = Integer.parseInt(readLine[0]);
                int y = Integer.parseInt(readLine[1]);
                this.click(x,y);
            }

        }
        catch (Exception e )
        {}

    }

    public boolean isInt(String s)
    {
        boolean isInt = false;
        try
        {
            Integer.parseInt(s);
            isInt = true;
        }
        catch(NumberFormatException e)
        {

        }
        return isInt;
    }

    public void help()
    {
        System.out.println("x y -- open the cell at the position (x,y)");
        System.out.println("F x y -- flag the cell at the position (x,y)");
        System.out.println("help -- get help");
        System.out.println("quit -- quit the game");
        System.out.println("exit -- quit the game");
    }

    public void setGameState(boolean gameState)
    {
        this.gameState = gameState;
    }

    public void click(int xPosition, int yPosition)
    {
        numOfClick++;
        if(gameBoard.click(xPosition-1, yPosition-1))
         {
             if(first)
             {
                 gameBoard.moveMine(xPosition-1,yPosition-1);
                 first=false;
                 //this.click(xPosition,yPosition);
             }
             else
             {
                 this.setGameState(false);
                 lost = true;
                 this.lost();
             }

         }
         boolean flag = true;
         for(int i=0; i<gameBoard.getHeight(); i++)
         {
             for(int j=0; j<gameBoard.getWidth(); j++)
             {
                 if(!gameBoard.getCell(i,j).isMine() && !gameBoard.getCell(i,j).isVisible())
                     flag = false;
             }
         }
         if(flag)
         {
             this.setGameState(false);
             this.win();
         }
         first = false;
    }

    public void flag(int xPosition, int yPosition)
    {
        Cell cell = gameBoard.getCell(xPosition-1,yPosition-1);
        if(cell.isVisible())
        {
            ;
        }
        else
        {
            if(cell.isFlagged())
            {
                cell.setFlagged(false);
            }
            else
            {
                cell.setFlagged(true);
                gameBoard.setNumOfLeftMines();
            }
        }
    }

    public void lost()
    {
        System.out.println("You lost the game :( :( :(");
    }

    public void win()
    {
        System.out.println("You win the game :) :) :)");
    }

    public static void main(String[] args)
    {
        try {
            MyMinesweeper gameController = new MyMinesweeper(8, 8, 10);
            //MyMinesweeper gameController = new MyMinesweeper(7, 7);
            gameController.play();
        } catch (InvalidRangeException e) {
            System.out.println("A different Exception has been thrown");
            //e.printStackTrace();
        }
    }
}
