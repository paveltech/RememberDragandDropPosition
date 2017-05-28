package com.interfaceitems;

/**
 * Created by android on 5/28/2017.
 */

public interface ItemTouchHelperViewHolder {

    /**
     *
     * called when the first regester and item as being or swiped
     */

    void onItemSelected();


    /**
     * called when state should clear
     */

    void onItemClear();

}
