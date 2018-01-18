package com.app.ecosurvey.ui.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Dell on 1/4/2018.
 */

class circle extends View
{
    private Paint paint;

    public circle(Context context, int x, int y)
    {
        super(context);
        paint = new Paint();
        // PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);


        paint.setAlpha(255);
        // paint.setXfermode(xfermode);
        paint.setAntiAlias(true);
        // setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawCircle(100, 100, 50, paint);
    }
}