import java.awt.Color;
/**
 * Creates a Tetrad made of 4 blocks for the Tetris game. 
 * 
 * @author Damini Kaushik
 * @version 20 January 2018
 */
public class Tetrad
{
    
    private Block[]blocks;
    private MyBoundedGrid<Block>grid;
    private int shape;
    private final static Color[]BLOCK_COLORS = {Color.RED, Color.GRAY,
                                                Color.CYAN, Color.YELLOW,
                                                Color.MAGENTA, Color.BLUE,
                                                Color.GREEN};

    /**
     * Constructor for objects of class Tetrad
     * 
     * @param gr the grid into which the Tetrad will be put
     */
    public Tetrad(MyBoundedGrid<Block>gr)
    {
        // initialise instance variables
        blocks = new Block[4];
        grid = gr;
        shape = (int)(Math.random()*7);
        Location[]locs=new Location[4];
        int cols = gr.getNumCols();
        if (shape == 0)
        {
            //I
            locs[0]=new Location(1,cols/2-1);
            locs[1]=new Location(0,cols/2-1);
            locs[2]=new Location(2,cols/2-1);
            locs[3]=new Location(3,cols/2-1);
        }
        if (shape == 1)
        {
            //T
            locs[0]=new Location(0,cols/2-1);
            locs[1]=new Location(0,cols/2-2);
            locs[3]=new Location(0,cols/2);
            locs[2]=new Location(1,cols/2-1);
        }
        if (shape == 2)
        {
            //O
            locs[0]=new Location(0,cols/2);
            locs[1]=new Location(0,cols/2-1);
            locs[2]=new Location(1,cols/2-1);
            locs[3]=new Location(1,cols/2);
        }
        if (shape == 3)
        {
            //L
            locs[0]=new Location(1,cols/2-1);
            locs[1]=new Location(0,cols/2-1);
            locs[2]=new Location(2,cols/2-1);
            locs[3]=new Location(2,cols/2);
        }
        if (shape == 4)
        {
            //J
            locs[0]=new Location(1,cols/2);
            locs[1]=new Location(0,cols/2);
            locs[2]=new Location(2,cols/2);
            locs[3]=new Location(2,cols/2-1);
        }
        if (shape == 5)
        {
            //S
            locs[0]=new Location(0,cols/2-1);
            locs[1]=new Location(0,cols/2);
            locs[2]=new Location(1,cols/2-1);
            locs[3]=new Location(1,cols/2-2);
        }
        if (shape == 6)
        {
            //Z
            locs[0]=new Location(0,cols/2-1);
            locs[1]=new Location(0,cols/2-2);
            locs[2]=new Location(1,cols/2-1);
            locs[3]=new Location(1,cols/2);
        }
        addToLocations(gr, locs);
    }

    /**
     * Puts the tetrad blocks at the given location
     * 
     * @param gr   the grid into which the blocks will be put
     * @param locs the locations for the blocks
     * @precondition the tetrad blocks are not in any grid
     */
    private void addToLocations(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for (int i=0; i<locs.length; i++)
        {
            blocks[i]=new Block();
            blocks[i].putSelfInGrid(gr, locs[i]);
            blocks[i].setColor(BLOCK_COLORS[shape]);
        }
    }

    /**
     * Removes the tetrad blocks from the grid and returns their locations
     * 
     * @return the array of the locations where the blocks formerly were
     * @precondition the blocks are in the grid
     */
    private Location[] removeBlocks()
    {
        Location[]locs = new Location[4];
        for (int i=0; i<blocks.length; i++)
        {
            locs[i]=blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    /**
     * Determines if the given locations are empty
     * 
     * @return true if the locations are empty; otherwise,
     *         false
     * @param  gr   the grid of the blocks that are to be checked
     *         locs the locations that are to be checked
     */
    private boolean areEmpty(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for (int i=0; i<locs.length; i++)
        {
            if (!gr.isValid(locs[i]) || gr.get(locs[i])!=null)
                return false;
        }
        return true;
    }

    /**
     * Moves the tetrad by a given number of rows and columns if possible
     * 
     * @return true if the tetrad can be moved; otherwise,
     *         false
     * @param  deltaRow the number of rows to move the tetrad
     * @param  deltaCol the number of columns to move the tetrad
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        Location[]newLocs = new Location[4];
        for (int i=0; i<newLocs.length; i++)
        {
            Location loc = blocks[i].getLocation();
            if (loc==null)
                return false;
            int row = loc.getRow();
            int col = loc.getCol();
            newLocs[i]=new Location(row+deltaRow, col+deltaCol);
            if (!grid.isValid(newLocs[i]))
                return false;
        }
        Location[]locs = removeBlocks();
        if (!areEmpty(grid, newLocs))
        {
            addToLocations(grid,locs);
            return false;
        }
        else
            addToLocations(grid,newLocs);
        return true;
    }

    /**
     * Rotates the tetrad counterclockwise by 90 degrees if possible
     * 
     * @return true if the tetrad can be rotated; otherwise,
     *         false
     */
    public boolean rotate()
    {
        if (shape==2)
            return true;
        Location[]newLocs = new Location[4];
        for (int i=0; i<newLocs.length; i++)
        {
            Location loc = blocks[i].getLocation();
            Location loc0 = blocks[0].getLocation();
            if (loc==null || loc0 == null)
                return false;
            int row = loc.getRow();
            int col = loc.getCol();
            int row0 = loc0.getRow();
            int col0 = loc0.getCol();
            newLocs[i]=new Location(row0-col0+col, row0+col0-row);
            if (!grid.isValid(newLocs[i]))
                return false;
        }
        Location[]locs = removeBlocks();
        if (!areEmpty(grid, newLocs))
        {  
            addToLocations(grid,locs);
            return false;
        }
        else
            addToLocations(grid,newLocs);
        return true;
    }
}