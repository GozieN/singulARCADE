package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MemoryBoardGameAndManagerAndTileTest {

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

        //These tests are for BoardManager class
        /**
         * Shuffle a few tiles.
         */
        private void solvedBoard() {
            MemoryGameBoard.setDimensions(4);
            List<MemoryPuzzleTile> tiles = makeTiles();
            MemoryGameBoard board = new MemoryGameBoard(tiles);
            boardManager = new MemoryBoardManager(board);
            final int numTiles = 16;
            for (int tileNum = 0; tileNum != numTiles; tileNum++) {
                MemoryPuzzleTile newTile = tiles.get(tileNum);
                newTile.setTopLayer(38);
                tiles.set(tileNum, newTile);
            }
            MemoryGameBoard newBoard = new MemoryGameBoard(tiles);
            boardManager.setBoard(newBoard);
        }

        /**
         * Test whether swapping two tiles makes a solved board unsolved.
         */
        @Test
        public void testIsOver() {
            setUpCorrect();
            assertEquals(false, boardManager.isOver());
        }

        /**
         * Test whether swapping the first two tiles works.
         */
        @Test
        public void numTileFlipped() {
            setUpCorrect();
            assertEquals(0, boardManager.numTileFlipped());
            // flip 2 tiles
            boardManager.flipTile(1);
            boardManager.flipTile(2);
            assertEquals(2, boardManager.numTileFlipped());
        }

        /**
         * Test whether isValidTap works.
         */
        @Test
        public void testIsValidTap() {
            setUpCorrect();
            MemoryPuzzleTile t1 = new MemoryPuzzleTile(1);
            MemoryPuzzleTile t2 = new MemoryPuzzleTile(2);
            assertEquals(true, boardManager.isValidTap(11));
            assertEquals(true, boardManager.isValidTap(14));
            //boardManager.greyOut(t1, t2);
//           assertEquals(false, boardManager.isValidTap(0));
            //need to flip 2 tiles that match
        }

        @Test
        public void testGreyOut() {
            setUpCorrect();
            MemoryPuzzleTile t1 = new MemoryPuzzleTile(1);
            MemoryPuzzleTile t2 = new MemoryPuzzleTile(0);
            boardManager.greyOut(t1, t2);
            assertEquals(true, boardManager.isValidTap(1));
            assertEquals(true, boardManager.isValidTap(14));

        }

        /**
         * Test whether a board is being set and returned properly (getter and setter of board works).
         */
        @Test
        public void testGetAndSetBoard() {
            setUpCorrect();
            MemoryGameBoard.setDimensions(4);
            List<MemoryPuzzleTile> tiles = makeTiles();
            MemoryGameBoard board = new MemoryGameBoard(tiles);
            MemoryGameBoard.setDimensions(5);
            boardManager.setBoard(board);
            assertEquals(board, boardManager.getBoard());
            MemoryGameBoard.setDimensions(6);
            boardManager.setBoard(board);
            assertEquals(board, boardManager.getBoard());
        }

//        /**
//         * Test whether testFlipTile works
//         */
//        @Test
//        public void testFlipTile() {
//            MemoryGameBoard.setDimensions(4);
//            boardManager.flipTile(1);
//            assertEquals(MemoryGameBoard, boardManager.getBoard());
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

        /**
         * Test whether getScore works
         */
        @Test
        public void testGetScore() {
            setUpCorrect();
            User user = new User("a", "1");
            GameLauncher.setCurrentUser(user);
            MemoryBoardManager boardManager = (MemoryBoardManager) GameLauncher.getCurrentUser().getRecentManagerOfBoard(MemoryBoardManager.GAME_NAME);

            //When no moves have been completed
            assertEquals(-1, boardManager.getScore());

            //When you have completed some moves on a 4x4 board
            ArrayList arrayList = new ArrayList();
            arrayList.add(1);
            arrayList.add(1);
            arrayList.add(2);
            arrayList.add(3);
            user.pushGameStates(MemoryBoardManager.GAME_NAME, arrayList);
            user.pushGameStates(MemoryBoardManager.GAME_NAME, arrayList);
            user.pushGameStates(MemoryBoardManager.GAME_NAME, arrayList);
            assertEquals(3333, boardManager.getScore());

            //When you have completed some moves on a 5x5 board
            MemoryGameBoard.setDimensions(5);
            assertEquals(6667, boardManager.getScore());

            //When you have completed some moves on a 6x6 board
            MemoryGameBoard.setDimensions(6);
            assertEquals(10000, boardManager.getScore());

        }

        @Test
        /**
         * Test numberOfMatches works
         */
        public void TestNumberOfMatches() {
            setUpCorrect();
            assertEquals(0, boardManager.numberOfMatches());
        }

        //These tests are for Tile class:
        /**
         * Test whether the constructor of Tiles class works
         */
        @Test
        public void testSetUpTiles() {
            MemoryGameBoard.setDimensions(6);
            List<MemoryPuzzleTile> tiles = makeTiles();
            MemoryGameBoard board = new MemoryGameBoard(tiles);
            assertEquals(36, board.numTiles());
            MemoryPuzzleTile memTile = new MemoryPuzzleTile(35);
            assertEquals(R.drawable.memory_tile_36, memTile.getBackground());
            assertEquals(R.drawable.memory_tile_37, memTile.getTopLayer());
        }

        //These tests are for Tile class:
        /**
         * Test whether the constructor of Tiles class works
         */
        @Test
        public void testDimFive() {
            MemoryGameBoard.setDimensions(5);
            List<MemoryPuzzleTile> tiles = makeTiles();
            MemoryGameBoard board = new MemoryGameBoard(tiles);
            assertEquals(25, board.numTiles());
            MemoryPuzzleTile memTile = new MemoryPuzzleTile(37);
            assertEquals(R.drawable.memory_tile_38, memTile.getBackground());
        }

        

        /**
         * Test whether compareTo works
         */
        @Test
        public void testCompareTo() {
            setUpCorrect();
            MemoryPuzzleTile comparingTile = boardManager.getBoard().getMemoryGameTile(1,1);
            MemoryPuzzleTile comparingTile2 = boardManager.getBoard().getMemoryGameTile(3,3);
            assertEquals(-1, boardManager.getBoard().getMemoryGameTile(0,0).compareTo(comparingTile));
        }
    }

