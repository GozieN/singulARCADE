package fall2018.csc2017.slidingtiles;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

//This extension of GridView contains built in logic for handling swipes between buttons.
public class GestureDetectGridView extends GridView {
    /**
     * The minimum swiping distance
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /**
     * The gesture detector
     */
    private GestureDetector gDetector;
    /**
     * The movement controller that processes all movements for the games
     */
    private MovementController mController;
    /**
     * The movement fling confirmation
     */
    private boolean mFlingConfirmed = false;
    /**
     * The x-coordinate location of a tap
     */
    private float mTouchX;
    /**
     * The y-coordinate location of a tap
     */
    private float mTouchY;
    /**
     * The game that's being played
     */
    private Game manager;

    /**
     * Initializes the Gesture Detect Grid View
     *
     * @param context the context of the activity
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initializes the Gesture Detect Grid View
     *
     * @param context the context of the activity
     * @param attrs   the Attribute Set
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initializes the Gesture Detect Grid View
     *
     * @param context      the context of the activity
     * @param attrs        the Attribute Set
     * @param defStyleAttr the def style attribute
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Detects taps and uses a movement controller to process the tap movements
     *
     * @param context the context of the activity
     */
    private void init(final Context context) {
        mController = new MovementController();
        mController.setBoardManager(manager);
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Sets the manager for DetectGridView
     *
     * @param manager the Game manager
     */
    public void setManager(Game manager) {
        this.manager = manager;
        mController.setBoardManager(manager);
    }
}
