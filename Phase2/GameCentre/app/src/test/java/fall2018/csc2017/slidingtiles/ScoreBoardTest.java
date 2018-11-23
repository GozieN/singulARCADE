package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        scoreBoard.takeNewScore(BoardManager.GAME_NAME, 100);
        assertEquals(1, scoreBoard.getTopScores().size());
        scoreBoard.takeNewScore("Testing", 200);
        assertEquals(2, scoreBoard.getTopScores().size());
    }

    @Test
    public void testReplaceScore() {
        setUpCorrect();
        //scoreboard is not full
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 250);
        assertEquals(1, scoreBoard.getTopScores().size());

        //scoreboard is full
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 222);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 223);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 224);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 225);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 226);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 227);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 228);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 229);
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 230);
        assertEquals(1, scoreBoard.getTopScores().size());
        scoreBoard.replaceScore(BoardManager.GAME_NAME, 275);
        assertEquals(1, scoreBoard.getTopScores().size());
    }

    @Test
    public void testToString() {
        setUpCorrect();
        //no scores on the scoreboard
        assertEquals("NO HIGH SCORES YET!", scoreBoard.toString());

        //one score on the scoreboard
        scoreBoard.takeNewScore(BoardManager.GAME_NAME, 100);
        StringBuilder s = new StringBuilder();
        s.append("1. ");
        s.append("Sliding Tiles");
        s.append(" : ");
        s.append(100);
        s.append(System.getProperty("line.separator"));
        assertEquals(s.toString(), scoreBoard.toString());
    }
}
