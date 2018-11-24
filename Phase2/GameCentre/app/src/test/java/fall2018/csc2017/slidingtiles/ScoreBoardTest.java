package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Unit tests for the User class.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ScoreBoardTest {
    /** The score board for testing. */
    ScoreBoard scoreBoard;

    /**
     * Make a user.
     */
    private void setUpCorrect() {

        scoreBoard = new ScoreBoard();

    }

    @Test
    public void testTakeNewScore() {
        setUpCorrect();
        //subject already exists in the scoreboard
        scoreBoard.takeNewScore(SlidingTilesManager.GAME_NAME, 100);
        assertEquals(1, scoreBoard.getTopScores().size());

        //add a new score to the scoreboard that is higher than the previous one
        scoreBoard.takeNewScore(SlidingTilesManager.GAME_NAME, 200);
        assertEquals(1, scoreBoard.getTopScores().size());

        //new subject in the scoreboard
        scoreBoard.takeNewScore("Testing", 200);
        assertEquals(2, scoreBoard.getTopScores().size());
    }

    @Test
    public void testReplaceScore() {
        setUpCorrect();
        //scoreboard is not full
        scoreBoard.replaceScore(SlidingTilesManager.GAME_NAME, 250);
        assertEquals(1, scoreBoard.getTopScores().size());

        //scoreboard is full
        scoreBoard.replaceScore("NewGameName1", 222);
        scoreBoard.replaceScore("NewGameName2", 223);
        scoreBoard.replaceScore("NewGameName3", 224);
        scoreBoard.replaceScore("NewGameName4", 225);
        scoreBoard.replaceScore("NewGameName5", 226);
        scoreBoard.replaceScore("NewGameName6", 227);
        scoreBoard.replaceScore("NewGameName7", 228);
        scoreBoard.replaceScore("NewGameName8", 229);
        scoreBoard.replaceScore("NewGameName9", 230);
        assertEquals(10, scoreBoard.getTopScores().size());

        scoreBoard.replaceScore("NewGameName10", 300);
        assertEquals(10, scoreBoard.getTopScores().size());
    }

    @Test
    public void testToString() {
        setUpCorrect();
        //no scores on the scoreboard
        assertEquals("NO HIGH SCORES YET!", scoreBoard.toString());

        //one score on the scoreboard
        scoreBoard.takeNewScore(SlidingTilesManager.GAME_NAME, 100);
        StringBuilder s = new StringBuilder();
        s.append("1. ");
        s.append("Sliding Tiles");
        s.append(" : ");
        s.append(100);
        s.append(System.getProperty("line.separator"));
        assertEquals(s.toString(), scoreBoard.toString());
    }
}
