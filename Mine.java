package model;

import model.Cell;

public class Mine extends Cell
{
    public Mine(int xPosition, int yPosition)
    {
        super(xPosition, yPosition);
    }

    @Override
    public boolean click() {
        return true;
    }

    @Override
    public boolean isMine() {
        return true;
    }
}