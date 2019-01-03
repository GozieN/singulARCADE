package fall2018.csc2017.slidingtiles.Games;

import java.io.Serializable;

import fall2018.csc2017.slidingtiles.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public abstract class Tile implements Serializable {

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
     * Set the background id.
     *
     * @param background background id to be set to
     */
    public void setBackground(int background) {
        this.background = background;
    }

    /**
     * Set the id of this Tile
     *
     * @param id id to be set to
     */
    public void setId(int id) {
        this.id = id;
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
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public Tile(int id, int background) {
        this.id = id + 1;
        this.background = R.drawable.tile_25;
    }


    /**
     * A Tile with id.
     *
     * @param id the id
     */
    public Tile(int id) {
        this.id = id;
        this.background = R.drawable.tile_full;
    }


}
