/**
 * Tetris creates a tetris game
 * 
 * @author Damini Kaushik
 *    with assistance from Annabelle Perng
 * @version 20 January 2018
 */
public class Tetris implements ArrowListener
{
    private MyBoundedGrid<Block>grid;
    private BlockDisplay display;
    private Tetrad tetrad;
    private boolean play;
    private int score;
    private int changeSpeed;
    private int level;
    private int completedRows;
    
    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        grid = new MyBoundedGrid<Block>(20,10);
        display = new BlockDisplay(grid);
        display.setArrowListener(this);
        score = 0;
        display.setTitle("level: " + level + " score: " + score);
        tetrad = new Tetrad(grid);
        display.showBlocks();
        changeSpeed = 0;
        level = 1;
        play = true;
    }

    /**
     * Responds to the up arrow.
     */
    @Override
    public void upPressed()
    {   
        if (!play)
            return;
        tetrad.rotate();
        display.showBlocks();
    }

    /**
     * Responds to the down arrow.
     */
    @Override
    public void downPressed()
    {
        if (!play)
            return;
        tetrad.translate(1,0);
        display.showBlocks();
    }

    /**
     * Responds to the left arrow.
     */
    @Override
    public void leftPressed()
    {
        if (!play)
            return;
        tetrad.translate(0,-1);
        display.showBlocks();
    }

    /**
     * Responds to the right arrow.
     */
    @Override
    public void rightPressed()
    {
        if (!play)
            return;
        tetrad.translate(0,1);
        display.showBlocks();
    }
    
    /**
     * Responds to the spacebar.
     */
    @Override
    public void spacePressed()
    {
        if (!play)
            return;
        tetrad.translate(3,0);
        display.showBlocks();
    }
    
    /**
     * Repeatedly moves the tetrad down or creates new tetrads until the game is over
     */
    public void play()
    {
        while (play)
        {
            while (tetrad.translate(1,0))
            {
                try 
                {
                    // Pause for 1000 milliseconds
                    Thread.sleep(1000 + changeSpeed);
                    display.showBlocks();
                    score+=1;
                    clearCompletedRows();
                    display.setTitle("level: " + level + " score: " + score);
                }
                catch(InterruptedException e)
                {
                    // ignore
                }
            }
            if (isGameOver())
            {
                play=false;
                System.out.println("YOU LOST!!!");
            }
            else
            {
                tetrad = new Tetrad(grid);
                display.showBlocks();
            }
        }
    }
    
    /**
     * Determines if the game is over by checking if there is a block in the top row 
     * 
     * @return true if the game is over; otherwise,
     *         false
     */
    private boolean isGameOver()
    {
        for (int c=0; c<grid.getNumCols(); c++)
        {
            Location loc = new Location(0, c);
            if (!(grid.get(loc)==null))
                return true;
        }
        return false;
    }

    /**
     * Determines if the given row is filled
     * 
     * @return true if all the locations in the row have blocks; otherwise,
     *         false
     * @precondition row is in the range of [0, number of rows)
     */
    private boolean isCompletedRow(int row)
    {
        for (int c=0; c<grid.getNumCols(); c++)
        {
            Location loc = new Location(row, c);
            if (grid.get(loc)==null)
                return false;
        }
        return true;
    }

    /**
     * Removes all the blocks in a row
     * 
     * @param row the row from which to remove the blocks
     * @precondition row is in the range of [0, number of rows)
     *               row is filled with blocks
     */
    private void clearRow(int row)
    {
        for (int c=0; c<grid.getNumCols(); c++)
        {
            Location loc = new Location(row, c);
            grid.remove(loc);
            for (int r=row-1; r>=0; r--)
            {
                Location loc2 = new Location (r,c);
                if (!(grid.get(loc2)==null))
                {
                    Location move = new Location(r+1, c);
                    Block block = grid.get(loc2);
                    block.moveTo(move);
                }
            }
        }
    }
    
    /**
     * Removes all the blocks from the rows that are completed
     */
    private void clearCompletedRows()
    {
        for (int r=0; r<grid.getNumRows(); r++)
        {
            if (isCompletedRow(r))
            {
                clearRow(r);
                if (level == 1)
                    score += 40 * level;
                if (level == 2)
                    score+=100*level;
                if (level==3)
                    score+=300*level;
                if (level==4)
                    score+=1200*level;
                completedRows++;
                if (completedRows==10)
                {
                    level++;
                    changeSpeed+=200;
                    completedRows=0;
                }
            }
        }
    }
    
    /**
     * Runs the tetris game
     * 
     * @param args arguments from the command line 
     */
    public static void main(String[]args)
    {
        Tetris tetris = new Tetris();
        tetris.play();
    }
}