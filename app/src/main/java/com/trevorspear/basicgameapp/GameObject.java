package com.trevorspear.basicgameapp;

import android.graphics.Canvas;

/**
 * Created by TrevorSpear on 4/3/17.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
