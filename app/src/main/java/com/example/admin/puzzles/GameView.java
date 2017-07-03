package com.example.admin.puzzles;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View implements View.OnTouchListener {
    private Bitmap bmp;
    private Field field;
    private Point first;
    private boolean scaled = false;
    private boolean preview = false;
    private boolean finished = false;
    private final int RECT_WIDTH = 2;

    final static String TOTAL_GAMES_EXTRA = "GAMES";
    final static String TOTAL_SECONDS_EXTRA = "TIME";

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bmp == null) return;
        if (!scaled) {
            bmp = Bitmap.createScaledBitmap(bmp, getWidth(), getHeight(), false);
            scaled = true;
        }

        int cellWidth = getWidth() / field.getWidth();
        int cellHeight = getHeight() / field.getHeight();

        int bmpWidth = bmp.getWidth() / field.getWidth();
        int bmpHeight = bmp.getHeight() / field.getHeight();

        Rect src = new Rect(), dst = new Rect();
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                dst.top = y*cellHeight;
                dst.bottom = (y+1)*cellHeight;
                dst.left = x*cellWidth;
                dst.right = (x+1)*cellWidth;

                int N;
                if (!preview)
                    N = field.getPuzzle(x, y).N;
                else
                    N = y*field.getWidth() + x;
                int srcX = N%field.getWidth();
                int srcY = N/field.getWidth();
                src.top = srcY*bmpHeight;
                src.bottom = (srcY+1)*bmpHeight;
                src.left = srcX*bmpWidth;
                src.right = (srcX+1)*bmpWidth;

                canvas.drawBitmap(bmp, src, dst, null);
                if (first != null && first.x == x && first.y == y) {
                    Paint paint = new Paint();
                    paint.setColor(Color.YELLOW);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(RECT_WIDTH);
                    dst.right -= RECT_WIDTH;
                    dst.bottom -= RECT_WIDTH;
                    canvas.drawRect(dst, paint);
                }
            }
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int cellWidth = getWidth() / field.getWidth();
        int cellHeight = getHeight() / field.getHeight();

        int x = (int)(event.getX() / cellWidth);
        int y = (int)(event.getY() / cellHeight);

        if (first == null) {
            first = new Point(x, y);
        } else {
            Point second = new Point(x, y);
            field.swap(first, second);
            first = null;
        }
        postInvalidate();
        if (field.check()) {
            Toast.makeText(getContext(), "FINISHED", Toast.LENGTH_LONG).show();
            if (!finished) {
                finished = true;
                updatePrefs();
            }
        }
        return false;
    }
    void init(Bitmap bmp, int NxN) {
        this.bmp = bmp;
        field = new Field(NxN, NxN);
        field.mix();
        postInvalidate();
    }
    void togglePreview() {
        preview = !preview;
    }
    void updatePrefs() {
        GameActivity act = (GameActivity) getContext();
        SharedPreferences prefs = act.getSharedPreferences("com.example.admin.puzzles", Context.MODE_PRIVATE);
        prefs.edit()
                .putInt(TOTAL_GAMES_EXTRA, prefs.getInt(TOTAL_GAMES_EXTRA, 0)+1)
                .putInt(TOTAL_SECONDS_EXTRA, prefs.getInt(TOTAL_SECONDS_EXTRA, 0)+act.getSeconds())
                .apply();
    }
    void newGame() {
        field.mix();
        finished = false;
    }
}
