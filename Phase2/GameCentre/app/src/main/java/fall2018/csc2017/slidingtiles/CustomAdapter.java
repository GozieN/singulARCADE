package fall2018.csc2017.slidingtiles;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java
*/

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

//This Class is an overwrite of the Base Adapter class
//It is designed to aid setting the button sizes and positions in the GridView
public class CustomAdapter extends BaseAdapter {
    /**
     * The array of buttons
     */
    private ArrayList<Button> mButtons;
    /**
     * the width and height of the columns
     */
    private int mColumnWidth, mColumnHeight;

    /**
     * @param buttons      the array of buttons
     * @param columnWidth  the columnWidth of the gesture detect grid
     * @param columnHeight the columnHeight of the gesture detect grid
     */
    CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
