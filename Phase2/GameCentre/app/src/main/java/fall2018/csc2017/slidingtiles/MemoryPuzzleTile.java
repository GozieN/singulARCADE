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


    //TODO: refactor this!!!!!!!!!!

    @Override
    public int compareTo(@NonNull MemoryPuzzleTile o) {
        //TODO
        return 0;
    }
}