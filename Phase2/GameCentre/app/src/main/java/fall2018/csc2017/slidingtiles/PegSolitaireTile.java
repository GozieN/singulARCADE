package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A PegSolitaire Tile in a sliding tiles puzzle.
 */
public class PegSolitaireTile implements Comparable<PegSolitaireTile>, Serializable {
    /**
     * The background id to find the peg solitaire tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    int id;

    /**
     * Tells whether the tile is highlighted or not.
     */
    boolean highlight;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the PegSolitaire tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A PegSolitaire tile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of the tile
     */
    PegSolitaireTile(int backgroundId) {
        this.id = backgroundId;
        background = R.drawable.tile_full;
    }
    @Override
    public int compareTo(@NonNull PegSolitaireTile o) {
        return o.id - this.id;
    }

    public void setId(int i, boolean highlight) {
        this.id = i;
        this.highlight = highlight;
        setBackground(this.id, this.highlight);
    }

    public void setBackground(int i, boolean highlight) {
        if (highlight) {
            switch (i) {
                case 1:
                    background = R.drawable.tile_emptyhighlight;
                    break;
                case 2:
                    background = R.drawable.tile_highlight;
                    break;
            }
        } else {
            switch (i) {
                case 0:
                    background = R.drawable.tile_dead;
                    break;
                case 1:
                    background = R.drawable.tile_empty;
                    break;
                case 2:
                    background = R.drawable.tile_full;
                    break;
            }
        }

    }
}
