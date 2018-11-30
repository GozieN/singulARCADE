package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MemoryBoardGameAndTileTest {

        /** The board manager for testing. */
        MemoryBoardManager boardManager;

        /**
         * Make a set of tiles that are in order.
         * @return a set of tiles that are in order
         */
        private List<MemoryPuzzleTile> makeTiles() {
            List<MemoryPuzzleTile> tiles = new ArrayList<>();
            final int numTiles = MemoryGameBoard.NUM_ROWS * MemoryGameBoard.NUM_COLS;
            for (int tileNum = 0; tileNum != numTiles; tileNum++) {
                tiles.add(new MemoryPuzzleTile(tileNum));
            }

            return tiles;
        }

        /**
         * Make a Board.
         */
        private void setUpCorrect() {
            MemoryGameBoard.setDimensions(4);
            List<MemoryPuzzleTile> tiles = makeTiles();
            MemoryGameBoard board = new MemoryGameBoard(tiles);
            boardManager = new MemoryBoardManager(board);
        }

//        //These tests are for BoardManager class
//        /**
//         * Shuffle a few tiles.
//         */
//        private void solvedBoard() {
//            List<MemoryPuzzleTile> tiles = new ArrayList<>();
//            final int numTiles = MemoryGameBoard.NUM_ROWS * MemoryGameBoard.NUM_COLS;
//            for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//                tiles.get(tileNum).setTopLayer(38);
//            }
//        }

//        /**
//         * Test whether swapping two tiles makes a solved board unsolved.
//         */
//        @Test
//        public void testIsOver() {
//            setUpCorrect();
//            assertEquals(false, boardManager.isOver());
//            solvedBoard();
//            assertEquals(true, boardManager.isOver());
//        }
//
//        /**
//         * Test whether swapping the first two tiles works.
//         */
//        @Test
//        public void numTileFlipped() {
//            setUpCorrect();
//            assertEquals(0, boardManager.numTileFlipped());
//            // flip 2 tiles
//            boardManager.flipTile(1);
//            boardManager.flipTile(2);
//            assertEquals(2, boardManager.numTileFlipped());
//        }
//
//        /**
//         * Test whether isValidTap works.
//         */
//        @Test
//        public void testIsValidTap() {
//            setUpCorrect();
//            assertEquals(true, boardManager.isValidTap(11));
//            assertEquals(true, boardManager.isValidTap(14));
//            //flip 2 matching ones
//            assertEquals(false, boardManager.isValidTap(1));
//        }
//
//        /**
//         * Test whether a board is being set and returned properly (getter and setter of board works).
//         */
//        @Test
//        public void testGetAndSetBoard() {
//            setUpCorrect();
//            MemoryGameBoard.setDimensions(4);
//            List<MemoryPuzzleTile> tiles = makeTiles();
//            MemoryGameBoard board = new MemoryGameBoard(tiles);
//            MemoryGameBoard.setDimensions(5);
//            boardManager.setBoard(board);
//            assertEquals(board, boardManager.getBoard());
//            MemoryGameBoard.setDimensions(6);
//            boardManager.setBoard(board);
//            assertEquals(board, boardManager.getBoard());
//        }
//
//        /**
//         * Test whether testFlipTile works
//         */
//        @Test
//        public void testFlipTile() {
//            MemoryGameBoard.setDimensions(4);
//            boardManager.flipTile(1);
//            assertEquals(MemoryGameBoard., boardManager.getBoard());
//        }
//
//        /**
//         * Test whether touchMove works
//         */
//        @Test
//        public void testFlipBack() {
//            setUpCorrect();
//
//        }
//
//        /**
//         * Test whether getScore works
//         */
//        @Test
//        public void testGetScore() {
//            setUpCorrect();
//            User user = new User("a", "1");
//            GameLauncher.setCurrentUser(user);
//            MemoryBoardManager boardManager = (MemoryBoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(MemoryBoardManager.GAME_NAME);
//
//            //When no moves have been completed
//            assertEquals(-1, boardManager.getScore());
//
//        }

        //These tests are for Tile class:
        /**
         * Test whether the constructor of Tiles class works
         */
        @Test
        public void testSetUpTiles() {
            MemoryGameBoard.setDimensions(5);
            List<MemoryPuzzleTile> tiles = makeTiles();
            MemoryGameBoard board = new MemoryGameBoard(tiles);
            assertEquals(25, board.numTiles());
            MemoryPuzzleTile memTile = new MemoryPuzzleTile(24);
            assertEquals(R.drawable.memory_tile_38, memTile.getBackground());
        }


        /**
         * Test whether compareTo works
         */
        @Test
        public void testCompareTo() {
            setUpCorrect();
            MemoryPuzzleTile comparingTile = boardManager.getBoard().getMemoryGameTile(1,1);
            assertEquals(-1, boardManager.getBoard().getMemoryGameTile(0,0).compareTo(comparingTile));
        }
    }

