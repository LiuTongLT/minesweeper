package model;

import gameinterface.Location;

public class MyLocation implements Location
{
    private int xPosition;
    private int yPosition;

    public MyLocation(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    @Override
    public int getRow() {
        return xPosition;
    }

    @Override
    public int getColumn() {
        return yPosition;
    }
}
