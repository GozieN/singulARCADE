package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

public class SlidingTiles extends Tile {
    SlidingTiles(int backgroundId) {
        super(backgroundId);
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return 0;
    }
}
