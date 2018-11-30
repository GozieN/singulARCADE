package fall2018.csc2017.slidingtiles;


import android.support.annotation.NonNull;

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


    /**
     * A Memory Puzzle tile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of the tile
     */
    MemoryPuzzleTile(int backgroundId) {
        super(backgroundId, -1);
        setTopLayer(R.drawable.memory_tile_37);
        String Name = "memory_tile_" + getId();
        try {
            setBackground(R.drawable.class.getField(Name).getInt(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(@NonNull MemoryPuzzleTile memoryPuzzleTile) {
        if ((getId() % 2 == 0 && getId() == memoryPuzzleTile.getId() + 1) ||
                (getId() % 2 != 0 && getId() == memoryPuzzleTile.getId() - 1)) {
            return 0;
        }
        return -1;
    }
}
