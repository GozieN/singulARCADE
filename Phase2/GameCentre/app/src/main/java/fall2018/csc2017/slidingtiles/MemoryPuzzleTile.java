package fall2018.csc2017.slidingtiles;


import android.support.annotation.NonNull;

import java.io.Serializable;

public class MemoryPuzzleTile extends Tile implements Comparable<MemoryPuzzleTile> {

    /**
     * The id of the topLayer displayed on the top of the board.
     */
    private int topLayer;

    /**
     * Return the topLayer id.
     *
     * @return the topLayer id
     */
    public int getTopLayer() {
        return topLayer;
    }

    /**
     * Set the topLayer id.
     *
     * @param topLayer the topLayer id
     */
    public void setTopLayer(int topLayer) {
        this.topLayer = topLayer;
    }


    /**
     * A MemoryPuzzleTile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    MemoryPuzzleTile(int id, int background) {
        super(id, background);
    }


    //TODO: fix this

    /**
     * A Memory Puzzle tile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of the tile
     */
    MemoryPuzzleTile(int backgroundId) {
        super(backgroundId, -1);
        setId(backgroundId + 1);
        setTopLayer(R.drawable.memory_tile_38);
        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                setBackground(R.drawable.memory_tile_1);
                break;
            case 2:
                setBackground(R.drawable.memory_tile_2);
                break;
            case 3:
                setBackground(R.drawable.memory_tile_3);
                break;
            case 4:
                setBackground(R.drawable.memory_tile_4);
                break;
            case 5:
                setBackground(R.drawable.memory_tile_5);
                break;
            case 6:
                setBackground(R.drawable.memory_tile_6);
                break;
            case 7:
                setBackground(R.drawable.memory_tile_7);
                break;
            case 8:
                setBackground(R.drawable.memory_tile_8);
                break;
            case 9:
                setBackground(R.drawable.memory_tile_9);
                break;
            case 10:
                setBackground(R.drawable.memory_tile_10);
                break;
            case 11:
                setBackground(R.drawable.memory_tile_11);
                break;
            case 12:
                setBackground(R.drawable.memory_tile_12);
                break;
            case 13:
                setBackground(R.drawable.memory_tile_13);
                break;
            case 14:
                setBackground(R.drawable.memory_tile_14);
                break;
            case 15:
                setBackground(R.drawable.memory_tile_15);
                break;
            case 16:
                setBackground(R.drawable.memory_tile_16);
                break;
            case 17:
                setBackground(R.drawable.memory_tile_17);
                break;
            case 18:
                setBackground(R.drawable.memory_tile_18);
                break;
            case 19:
                setBackground(R.drawable.memory_tile_19);
                break;
            case 20:
                setBackground(R.drawable.memory_tile_20);
                break;
            case 21:
                setBackground(R.drawable.memory_tile_21);
                break;
            case 22:
                setBackground(R.drawable.memory_tile_22);
                break;
            case 23:
                setBackground(R.drawable.memory_tile_23);
                break;
            case 24:
                setBackground(R.drawable.memory_tile_24);
                break;
            case 25:
                setBackground(R.drawable.memory_tile_25);
                break;
            case 26:
                setBackground(R.drawable.memory_tile_26);
                break;
            case 27:
                setBackground(R.drawable.memory_tile_27);
                break;
            case 28:
                setBackground(R.drawable.memory_tile_28);
                break;
            case 29:
                setBackground(R.drawable.memory_tile_29);
                break;
            case 30:
                setBackground(R.drawable.memory_tile_30);
                break;
            case 31:
                setBackground(R.drawable.memory_tile_31);
                break;
            case 32:
                setBackground(R.drawable.memory_tile_32);
                break;
            case 33:
                setBackground(R.drawable.memory_tile_33);
                break;
            case 34:
                setBackground(R.drawable.memory_tile_34);
                break;
            case 35:
                setBackground(R.drawable.memory_tile_35);
                break;
            case 36:
                setBackground(R.drawable.memory_tile_36);
                break;
            case 38:
                setBackground(R.drawable.memory_tile_38);
                break;
            default:
                setBackground(R.drawable.tile_blank);
        }
    }

//    /**
//     * A Memory Puzzle tile with a background id, set the id eaquals to the backgroundid
//     *
//     * @param backgroundId the background id of the tile
//     */
//    MemoryPuzzleTile(int backgroundId) {
//        id = backgroundId;
//        String fileName = "memory_tile_" + backgroundId;
//        background = App.getContext().getResources().getIdentifier(
//                fileName, "drawable", "fall2018.csc2017.slidingtiles");
//    }
//
//    public static void main(String[] args) {
//        MemoryPuzzleTile memo = new MemoryPuzzleTile(1);
//        memo.getBackground();

    @Override
    public int compareTo(@NonNull MemoryPuzzleTile memoryPuzzleTile) {
        if ((getId() % 2 == 0 && getId() == memoryPuzzleTile.getId() + 1) ||
                (getId() % 2 != 0 && getId() == memoryPuzzleTile.getId() - 1)) {
            return 0;
        }
        return -1;
    }
}

