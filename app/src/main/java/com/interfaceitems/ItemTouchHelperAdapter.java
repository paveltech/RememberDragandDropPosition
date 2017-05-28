package com.interfaceitems;

/**
 * Created by android on 5/28/2017.
 */

public interface ItemTouchHelperAdapter {

    /**
     * called when a item is dragged wnough to tragger a move
     */


    void onItemMove(int fromPosition , int toPosition);


    /**
     * called when a item is dismissed or swiped
     */

    void onitemDismiss(int position);

}
