package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import se.kth.csc.ubicomp.ubishopper.R;

/**
 * A customized grid view that allows Interest Grid items be selectable.
 *
 * Created by mhotan on 5/1/14.
 */
public class InterestGridItem extends RelativeLayout implements Checkable {

    public InterestGridItem(Context context) {
        super(context);
        setClickable(true);
    }

    public InterestGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public InterestGridItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setClickable(true);
    }

    @Override
    public void setChecked(boolean checked) {
        ImageView checkImage = (ImageView) findViewById(R.id.checkbox_image);
        checkImage.setVisibility(checked ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean isChecked() {
        ImageView checkImage = (ImageView) findViewById(R.id.checkbox_image);
        return checkImage.getVisibility() == View.VISIBLE;
    }

    @Override
    public void toggle() {
        // Use the two non dependent methods.
        setChecked(!isChecked());
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }
}
