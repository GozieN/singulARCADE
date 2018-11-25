package fall2018.csc2017.slidingtiles;


import android.support.annotation.NonNull;

import java.io.Serializable;

public class MemoryPuzzleTile implements Comparable<MemoryPuzzleTile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * set the id of the memory puzzle tile
     *
     * @param id the new id of the tile
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A MemoryPuzzleTile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    MemoryPuzzleTile(int id, int background) {
        this.id = id;
        this.background = background;
    }


    //TODO: fix this

    /**
     * A Memory Puzzle tile with a background id, set the id eaquals to the backgroundid
     *
     * @param backgroundId the background id of the tile
     */
    MemoryPuzzleTile(int backgroundId) {
        id = backgroundId;
        String fileName = "memory_tile_" + backgroundId;
        background = App.getContext().getResources().getIdentifier(
                fileName, "drawable", "fall2018.csc2017.slidingtiles");
    }

    public static void main(String[] args) {
        MemoryPuzzleTile memo = new MemoryPuzzleTile(1);
        memo.getBackground();

    }

    @Override
    public int compareTo(@NonNull MemoryPuzzleTile memoryPuzzleTile) {
        if ((id % 2 == 0 && id == memoryPuzzleTile.getId() + 1) ||
                (id % 2 != 0 && id == memoryPuzzleTile.getId() - 1)) {
            return 0;
        }
        return -1;
    }
}

