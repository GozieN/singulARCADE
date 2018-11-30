package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.ArrayList;


/**
 * A SlidingTile in a sliding tile puzzle game.
 */
public class SlidingTile extends Tile implements Comparable<SlidingTile> {

    /**
     * A SlidingTile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    SlidingTile(int id, int background) {
        super(id, background);
    }


    // Adapted from stackoverflow https://stackoverflow.com/questions/6583843/how-to-access-resource-with-dynamic-name-in-my-case

    /**
     * A SlidingTile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of the tile
     */
    SlidingTile(int backgroundId) {
        super(backgroundId, -1);
        ArrayList tileList = new ArrayList<>();
        tileList.add(R.drawable.tile_1);
        String name = "tile_" + getId();
        try {
            setBackground(R.drawable.class.getField(name).getInt(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(@NonNull SlidingTile o) {
        return o.getId() - this.getId();
    }
}

