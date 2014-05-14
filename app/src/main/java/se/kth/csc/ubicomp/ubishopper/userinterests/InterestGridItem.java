package se.kth.csc.ubicomp.ubishopper.userinterests;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import se.kth.csc.ubicomp.ubishopper.R;
import se.kth.csc.ubicomp.ubishopper.model.InterestType;
import se.kth.csc.ubicomp.ubishopper.model.MockModel;

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

    private InterestType type;

    public void setInterestType(InterestType type) {
        this.type = type;
        TextView interestLabel = (TextView) findViewById(R.id.interest_grid_item_label);
        ImageView interestImage = (ImageView) findViewById(R.id.interest_image);

        // Populate the fields for the item
        interestLabel.setText(type.getName());
        type.loadImage(interestImage);
    }

    @Override
    public void setChecked(boolean checked) {
        ImageView checkImage = (ImageView) findViewById(R.id.checkbox_image);
        if (checked) {
            MockModel.getInstance().getUserIntereset().add(type);
        } else {
            MockModel.getInstance().getUserIntereset().remove(type);
        }
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
