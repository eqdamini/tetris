import java.util.ArrayList;

/**
 * MyBoundedGrid is the grid on which the game is played and on which the blocks exist.
 * It is a rectangular grid with a finite number of rows and columns.
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King
 * @author  Damini Kaushik
 * @version 9 January 2018
 * 
 * @param <E> the elements that may be put in the grid are any objects
 */
public class MyBoundedGrid<E>
{
    /**
     * The 2-D array that is used to store the grid's elements.
     */
    private Object[][] occupantArray; 

    /**
     * Constructs an empty MyBoundedGrid with the given dimensions.
     * 
     * @param rows  the grid's number of rows;  rows > 0 
     * @param cols  the grid's number of cols;  cols > 0
     */
    public MyBoundedGrid(int rows, int cols)
    {
        occupantArray = new Object[rows][cols];
    }

    /**
     * Retrieves the number of rows.
     * 
     * @return the grid's row count
     */
    public int getNumRows()
    {
        return occupantArray.length;
    }

    /**
     * Retrieves the number of columns.
     * 
     * @return the grid's columns count
     */
    public int getNumCols()
    {
        return occupantArray[0].length;
    }

    /**
     * Determines whether a location is valid.
     * 
     * @param  loc   the location in quesion.  loc != null
     * @return true  if loc is valid in this grid; otherwise, 
     *         false 
     */
    public boolean isValid(Location loc)
    {
        //if the coordinates are in range
        if (loc.getRow() >= getNumRows() || loc.getRow() < 0 ||
            loc.getCol() >= getNumCols() || loc.getCol() < 0)
            return false;
        return true;
    }

    /**
     * Retrieves an element from this grid at a location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * 
     * @return the object at location loc 
     *         or null if the location is unoccupied
     */
    public E get(Location loc)
    {
        if (isValid(loc))
        {
            E obj = (E) occupantArray[loc.getRow()][loc.getCol()];
            return obj;
        }
        return null;
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Puts an element at location loc on this grid.  Plus
     * returns the object previously at that location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * @param obj  the object to put at location loc
     * 
     * @return the object at location loc 
     *         or null if the location is unoccupied
     */
    public E put(Location loc, E obj)
    {
        if (isValid(loc))
        {
            E object1 = (E) occupantArray[loc.getRow()][loc.getCol()];
            occupantArray[loc.getRow()][loc.getCol()]=(Object)obj;
            return object1;
        }
        return null;
    }

    /**
     * Removes an element from this grid at a location. Plus
     * returns the object previously at that location, or
     * null if the location is unoccupied.
     * 
     * @param loc is a valid location in this grid
     * 
     * @return the object that was at location loc 
     *         or null if the location is unoccupied
     */
    public E remove(Location loc)
    {
        if (isValid(loc))
        {
            E obj = (E) occupantArray[loc.getRow()][loc.getCol()];
            occupantArray[loc.getRow()][loc.getCol()]=null;
            return obj;
        }
        return null;
    }

    /**
     * Returns all the occupied location in this grid.
     * 
     * @return all the occupied locations in an arry list; 
     *         0 <= list.size < getNumRows() * getNumCols()
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location>occupied = new ArrayList<Location>();
        for (int r=0; r<getNumRows(); r++)
        {
            for (int c=0; c<getNumCols(); c++)
            {
                if (occupantArray[r][c]!=null)
                {
                    Location loc = new Location(r,c);
                    occupied.add(loc);
                }
            }
        }
        return occupied;
    }
}