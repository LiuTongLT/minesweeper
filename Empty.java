package model;

public class Empty extends Cell
{
    public Empty(int xPosition, int yPosition)
    {
        super(xPosition, yPosition);
    }

    @Override
    public boolean click()
    {
        return super.click();
    }

    @Override
    public boolean isMine() {
        return false;
    }
}
