package com.ubaza.android.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ubaza.android.R;
import com.ubaza.android.ui.common.BaseFragment;

import hugo.weaving.DebugLog;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import com.ubaza.android.ui.CustomCard;

public class AlternativeFragment extends BaseFragment {

    public static AlternativeFragment newInstance() {
        return new AlternativeFragment();
    }

    @DebugLog
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_alternative, container, false );
        create_card( view, "Sumol", R.id.carddemo_example_card_expand1,R.drawable.doge2 );
        create_card( view, "Zara", R.id.carddemo_example_card_expand2, R.drawable.doge3 );
        create_card( view, "Galp", R.id.carddemo_example_card_expand3, R.drawable.doge4 );

        return view;
    }

    /**
     * This method builds a standard header with base expand/collapse
     */
    private void create_card( View view, String company, int id, int idImage ) {


        //Create a Card
        CustomCard card = new CustomCard( getActivity() );

        //Create a CardHeader
        CardHeader header = new CardHeader( getActivity() );

        //Set the header title
        header.setTitle( company );

        // The image
        CardThumbnail thumb = new CardThumbnail( getActivity() );
        thumb.setDrawableResource( idImage );
        card.addCardThumbnail( thumb );

        //Set visible the expand/collapse button
        header.setButtonExpandVisible( true );

        //Add Header to card
        card.addCardHeader( header );


        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand( getActivity() );
        //Set inner title in Expand Area
        expand.setTitle( "Here is some advertising text" );
        card.addCardExpand( expand );

        //Set card in the cardView
        CardView cardView = ( CardView ) view.findViewById( id );

        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .setupView(cardView);
        card.setViewToClickToExpand(viewToClickToExpand);

        cardView.setCard( card );
    }
}
