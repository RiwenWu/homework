package example.neihe_lab05;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wrw on 2017/3/30.
 */

public class MytextView_dialog_wifi extends TextView {


    public MytextView_dialog_wifi(Context context) {
        super(context);
    }

    public MytextView_dialog_wifi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private int sroke_width = 2;


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        //  将边框设为灰色
        paint.setColor(Color.GRAY);
        //  画TextView的4个边
        canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, paint);
        canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, paint);
        canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        canvas.drawLine(0, this.getHeight() - sroke_width, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        super.onDraw(canvas);
    }
}
