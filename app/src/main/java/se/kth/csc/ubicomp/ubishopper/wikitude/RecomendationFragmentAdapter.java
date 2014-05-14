package se.kth.csc.ubicomp.ubishopper.wikitude;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import se.kth.csc.ubicomp.ubishopper.R;

/**
 * Created by zenkig on 5/8/14.
 */

public class RecomendationFragmentAdapter extends BaseAdapter {

    private static final int[][] IMAGES = new int[][] {
            new int[] { R.raw.clothes, R.raw.iphone },
            new int[] { R.raw.fashionshoes, R.raw.guitar },
            new int[] { R.raw.vegetable, R.raw.drink } };

    private Context _context;

    public RecomendationFragmentAdapter( Context context ) {
        _context = context;
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object getItem( int position ) {
        return null;
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        View view = LayoutInflater.from( _context ).
                inflate( R.layout.fragment_page_flip, parent, false );

        ImageView leftImage = (ImageView) view.findViewById( R.id.left_image );
        leftImage.setImageResource( IMAGES[position][0] );

        ImageView rightImage = (ImageView) view.findViewById( R.id.right_image );
        rightImage.setImageResource( IMAGES[position][1] );

        return view;
    }

}
