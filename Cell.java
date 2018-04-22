package model;

public class Cell
{
    protected int xPosition;
    protected  int yPosition;
    protected boolean isFlagged;
    protected boolean isVisible;

    public Cell(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isFlagged = false;
        this.isVisible = false;
    }

    public boolean isFlagged()
    {
        return isFlagged;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean click()
    {
        return  true;
    }

    public boolean isMine()
    {
        return false;
    }
}