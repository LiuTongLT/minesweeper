package model;

import static jdk.dynalink.linker.support.Guards.isInstance;

public class Board
{
    private int numOfMines;
    private int height;
    private int width;
    private int numOfLeftMines;
    private Cell[][] cells;
    private Integer[][] nums;

    public Board(int height, int width, int numOfMines)
    {
        if(height >0)
            this.height = height;
        if(width > 0)
            this.width = width;
        if(numOfMines <= height*width)
            this.numOfMines = numOfMines;
        this.numOfLeftMines = this.numOfMines;
        cells = new Cell[this.height][this.width];
        nums = new Integer[this.height][this.width];
        for(int i=0; i<this.height; i++)
        {
            for(int j=0; j<this.width; j++)
                nums[i][j] = 0;
        }
        setBoard();
    }

    public Board(int height, int width)
    {
        this.height = height;
        this.width = width;
        numOfMines = height*width/6;
        numOfLeftMines = numOfMines;
        cells = new Cell[height][width];
        nums = new Integer[height][width];
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
                nums[i][j] = 0;
        }
        setBoard();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNumOfLeftMines() {
        return numOfLeftMines;
    }

    public Cell getCell(int xPosition, int yPosition)
    {
        return cells[xPosition][yPosition];
    }

    public void setNumOfLeftMines()
    {
        if(numOfLeftMines>0)
        this.numOfLeftMines--;
    }

    public void setBoard()
    {
        double n = (double)numOfMines/(height*width);
        int mine = 0;
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                if(Math.random() < n && mine < numOfMines)
                {
                    cells[i][j] = new Mine(i, j);
                    mine++;
                }
                else
                {
                    cells[i][j] = new Empty(i, j);
                }
            }
        }
        if(mine<numOfMines)
        {
            for(int i=0; i<height; i++)
            {
                for(int j=0; j<width; j++)
                {
                    if(mine < numOfMines && !cells[i][j].isMine() && Math.random() < n)
                    {
                        cells[i][j] = new Mine(i, j);
                        mine++;
                    }
                }
            }
        }
    }

    public void display()
    {
        System.out.print(" ");
        for(int i=1; i<=width; i++)
            System.out.print(" " + i);
        System.out.println("");
        for(int i=0; i<height; i++)
        {
            System.out.print(i+1 + " ");
            for(int j=0; j<width; j++)
            {
                if( !cells[i][j].isVisible() )
                {
                   if(cells[i][j].isFlagged())
                   {
                       System.out.print("F ");
                   }
                   else
                   {
                       System.out.print("_ ");
                   }

                }
                else
                {
                    if(nums[i][j] == 0)
                        System.out.print("  ");
                    else
                        System.out.print(nums[i][j] + " ");
                }
            }
            System.out.println("");

        }
        System.out.println("Left mines: "+numOfLeftMines);
        System.out.println("Enter input:");
    }

    public int moveMine(int xPosition, int yPosition)
    {
        for(int i=0 ;i<=this.height-1;i++)
        {
            for(int j = 0;j<=this.width-1;j++)
            {
                if(!cells[i][j].isMine())
                {
                    cells[i][j]=new Mine(i,j);
                    this.cells[xPosition][yPosition]=new Empty(xPosition,yPosition);
                    this.click(xPosition,yPosition);
                    return 0;
                }

            }
        }
       return 0;
    }

    public int calculateNumOfMines(int x, int y)
    {
        int num = 0;
        if(x == 0 && y == 0)
        {
            if (cells[x + 1][y].isMine())
                num++;
            if (cells[x][y + 1].isMine())
                num++;
            if (cells[x + 1][y + 1].isMine())
                num++;
        }
        else if(x == 0 && y == width-1)
        {
            if (cells[x][y-1].isMine())
                num++;
            if (cells[x+1][y-1].isMine())
                num++;
            if (cells[x + 1][y].isMine())
                num++;
        }
        else if(x == height-1 && y == 0)
        {
            if (cells[x][y+1].isMine())
                num++;
            if (cells[x-1][y].isMine())
                num++;
            if (cells[x-1][y + 1].isMine())
                num++;
        }
        else if(x == height-1 && y == width-1)
        {
            if (cells[x][y-1].isMine())
                num++;
            if (cells[x-1][y-1].isMine())
                num++;
            if (cells[x-1][y].isMine())
                num++;
        }
        else if(x == 0 && y != 0 && y<width-1)
        {
            if(cells[x][y-1].isMine())  // isMine()
                num++;
            if(cells[x][y+1].isMine())
                num++;
            if(cells[x+1][y-1].isMine())
                num++;
            if(cells[x+1][y].isMine())
                num++;
            if(cells[x+1][y+1].isMine())
                num++;
        }
        else if(x != 0 && x < height-1 && y == 0)
        {
            if(cells[x-1][y].isMine())
                num++;
            if(cells[x-1][y+1].isMine())
                num++;
            if(cells[x][y+1].isMine())
                num++;
            if(cells[x+1][y].isMine())
                num++;
            if(cells[x+1][y+1].isMine())
                num++;
        }
        else if(x != 0 && x < height-1 && y == width-1)
        {
            if(cells[x-1][y-1].isMine())  // isMine()
                num++;
            if(cells[x][y-1].isMine())
                num++;
            if(cells[x+1][y-1].isMine())
                num++;
            if(cells[x-1][y].isMine())
                num++;
            if(cells[x+1][y].isMine())
                num++;
        }
        else if(x == height-1 && y != 0 && y<width-1)
        {
            if(cells[x-1][y-1].isMine())  // isMine()
                num++;
            if(cells[x-1][y].isMine())
                num++;
            if(cells[x-1][y+1].isMine())
                num++;
            if(cells[x][y-1].isMine())
                num++;
            if(cells[x][y+1].isMine())
                num++;
        }
        else
        {
            if(cells[x-1][y-1].isMine())
                num++;
            if(cells[x-1][y].isMine())
                num++;
            if(cells[x-1][y+1].isMine())
                num++;
            if(cells[x][y-1].isMine())
                num++;
            if(cells[x][y+1].isMine())
                num++;
            if(cells[x+1][y-1].isMine())
                num++;
            if(cells[x+1][y].isMine())
                num++;
            if(cells[x+1][y+1].isMine())
                num++;
        }
        return num;
    }

        public boolean click(int x, int y)
        {
            if(cells[x][y].isFlagged())
                return false;
            if(cells[x][y].isMine())
                return true;
            else if(!cells[x][y].isVisible())
            {
                cells[x][y].setVisible(true);
                nums[x][y] = calculateNumOfMines(x, y);
                if(nums[x][y] == 0)
                {
                    if(x == 0 && y == 0)
                    {
                        click(x, y+1);
                        click(x+1, y);
                        click(x+1, y+1);
                    }
                    else if(x == 0 && y == width-1)
                    {
                        click(x, y-1);
                        click(x+1, y-1);
                        click(x+1, y);
                    }
                    else if(x == height-1 && y == width-1)
                    {
                        click(x, y-1);
                        click(x-1, y-1);
                        click(x-1, y);
                    }
                    else if(x == height-1 && y == 0)
                    {
                        click(x, y+1);
                        click(x-1, y);
                        click(x-1, y+1);
                    }
                    else if(x == 0 && y != 0 && y < width-1)
                    {
                        click(x, y-1);
                        click(x, y+1);
                        click(x+1, y-1);
                        click(x+1, y);
                        click(x+1, y+1);
                    }
                    else if(x != 0 && x < height-1 && y == 0)
                    {
                        click(x-1, y);
                        click(x-1, y+1);
                        click(x, y+1);
                        click(x+1, y);
                        click(x+1, y+1);
                    }
                    else if(x == height-1 && y != 0 && y < width-1)
                    {
                        click(x-1, y-1);
                        click(x-1, y);
                        click(x-1, y+1);
                        click(x, y-1);
                        click(x, y+1);
                    }
                    else if(y == width-1 && x != 0 && x < height-1)
                    {
                        click(x-1, y-1);
                        click(x, y-1);
                        click(x+1, y-1);
                        click(x-1, y);
                        click(x+1, y);
                    }
                    else
                    {
                        click(x-1, y-1);
                        click(x-1, y);
                        click(x-1, y+1);
                        click(x, y-1);
                        click(x, y+1);
                        click(x+1, y-1);
                        click(x+1, y);
                        click(x+1, y+1);
                    }
                }
            }  //display() in GameControl
            return false;
        }

    public void overview()  //display all mines
    {
        System.out.print(" ");
        for (int i = 1; i < width+1; i++)
            System.out.print(" " + i);
        System.out.println("");
        for (int i = 0; i < height; i++)
        {
            System.out.print(i+1 + " ");
            for (int j = 0; j < width; j++) {
                if (cells[i][j].isMine())
                    System.out.print("* ");
                else
                    System.out.print("  ");
            }
            System.out.println("");
        }
    }
}