package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;



/**
 * A SlidingTile in a sliding tile puzzle game.
 */
public class SlidingTile extends Tile implements Comparable<SlidingTile> {

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return super.getBackground();
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return super.getId();
    }

    /**
     * A SlidingTile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    SlidingTile(int id, int background) {
        super(id, background);
    }

    /**
     * A SlidingTile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of the tile
     */
    SlidingTile(int backgroundId) {
        super(backgroundId);
    }

    @Override
    public int compareTo(@NonNull SlidingTile o) {
        return o.getId() - this.getId();
    }
}

