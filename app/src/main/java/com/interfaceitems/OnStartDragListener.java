package com.interfaceitems;

import android.support.v7.widget.RecyclerView;

/**
 * Created by android on 5/28/2017.
 */

public interface OnStartDragListener {


    /**
     * called when a item is start dragging
     */

    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
