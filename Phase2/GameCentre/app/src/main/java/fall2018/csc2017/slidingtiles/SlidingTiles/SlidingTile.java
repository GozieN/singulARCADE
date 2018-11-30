package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.ArrayList;


/**
 * A Sliding Tile in a sliding tile puzzle game.
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


    //TODO refactor this

    /**
     * A SlidingTile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of the tile
     */
    SlidingTile(int backgroundId) {
        super(backgroundId, -1);
        setId(backgroundId + 1);

        ArrayList tileList = new ArrayList<>();
        tileList.add(R.drawable.tile_1);


        // This looks so ugly.
        switch (backgroundId + 1) {
            case 1:
                setBackground(R.drawable.tile_1);
                break;
            case 2:
                setBackground(R.drawable.tile_2);
                break;
            case 3:
                setBackground(R.drawable.tile_3);
                break;
            case 4:
                setBackground(R.drawable.tile_4);
                break;
            case 5:
                setBackground(R.drawable.tile_5);
                break;
            case 6:
                setBackground(R.drawable.tile_6);
                break;
            case 7:
                setBackground(R.drawable.tile_7);
                break;
            case 8:
                setBackground(R.drawable.tile_8);
                break;
            case 9:
                setBackground(R.drawable.tile_9);
                break;
            case 10:
                setBackground(R.drawable.tile_10);
                break;
            case 11:
                setBackground(R.drawable.tile_11);
                break;
            case 12:
                setBackground(R.drawable.tile_12);
                break;
            case 13:
                setBackground(R.drawable.tile_13);
                break;
            case 14:
                setBackground(R.drawable.tile_14);
                break;
            case 15:
                setBackground(R.drawable.tile_15);
                break;
            case 16:
                setBackground(R.drawable.tile_16);
                break;
            case 17:
                setBackground(R.drawable.tile_17);
                break;
            case 18:
                setBackground(R.drawable.tile_18);
                break;
            case 19:
                setBackground(R.drawable.tile_19);
                break;
            case 20:
                setBackground(R.drawable.tile_20);
                break;
            case 21:
                setBackground(R.drawable.tile_21);
                break;
            case 22:
                setBackground(R.drawable.tile_22);
                break;
            case 23:
                setBackground(R.drawable.tile_23);
                break;
            case 24:
                setBackground(R.drawable.tile_24);
                break;
            default:
                setBackground(R.drawable.tile_blank);
        }
    }

    @Override
    public int compareTo(@NonNull SlidingTile o) {
        return o.getId() - this.getId();
    }
}

