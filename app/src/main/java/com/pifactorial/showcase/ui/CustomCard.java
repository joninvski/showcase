package com.pifactorial.showcase.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import com.pifactorial.showcase.R;
/**
 * This class provides an example of custom card with a custom inner layout.
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class CustomCard extends Card {

    protected TextView mTitle;
    protected TextView mSecondaryTitle;
    protected RatingBar mRatingBar;

    /**
     * Constructor with a custom inner layout
     *
     * @param context
     */
    public CustomCard( Context context ) {
        this( context, R.layout.carddemo_mycard_inner_content );
    }

    /**
     * @param context
     * @param innerLayout
     */
    public CustomCard( Context context, int innerLayout ) {
        super( context, innerLayout );
        init();
    }

    /**
     * Init
     */
    private void init() {

        //No Header

        /*
        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public void setupInnerViewElements( ViewGroup parent, View view ) {

        //Retrieve elements
        mTitle = ( TextView ) parent.findViewById( R.id.carddemo_myapps_main_inner_title );
        mSecondaryTitle = ( TextView ) parent.findViewById( R.id.carddemo_myapps_main_inner_secondaryTitle );


        if ( mTitle != null )
            mTitle.setText( "App name " );

        if ( mSecondaryTitle != null )
            mSecondaryTitle.setText( " Name 2 ");

    }
}
