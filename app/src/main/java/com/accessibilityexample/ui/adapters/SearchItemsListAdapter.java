package com.accessibilityexample.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.accessibilityexample.R;
import com.bumptech.glide.Glide;
import com.accessibilityexample.interfaces.ListItemAction;
import com.accessibilityexample.models.InnerSounds;

import java.util.List;

public class SearchItemsListAdapter extends RecyclerView.Adapter<SearchItemsListAdapter.RecyclerViewHolders> {

    private List<InnerSounds> itemList;
    private Context context;
    private ListItemAction listItemAction;
    private LayoutInflater mInflater;
    private RecyclerViewHolders holder;
    private boolean forPlay = false;
    private boolean hasItems = true;
    private int layoutResourceId = -1;
    private boolean isBig = false;






    public SearchItemsListAdapter(Context context, List<InnerSounds> itemList, ListItemAction listItemAction) {
        this.itemList = itemList;
        this.context = context;
        if (context != null)
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        layoutResourceId = R.layout.item_soundview;
        this.listItemAction = listItemAction;
    }


    @Override
    public SearchItemsListAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SearchItemsListAdapter.RecyclerViewHolders holder, int position) {
        InnerSounds sounds = itemList.get(position);
        Glide.with(context)
                .load(sounds.image)
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_new_releases_black_24dp)
                .centerCrop()
                .into(holder.ivSoundIcon);
        holder.ivSoundIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemAction.onClick(sounds,position);

            }
        });

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (itemList == null)
            return 0;
        return itemList.size();
    }

    private void setContent(final RecyclerViewHolders holder, final InnerSounds sounds, final int position) {

    }

    public List<InnerSounds> getItemList() {
        return itemList;
    }

    public void setItemList(List<InnerSounds> itemList) {
        this.itemList = itemList;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        public ImageView ivSoundIcon;
        //        private ProgressBar pdProgress;
        private LinearLayout llItemContainer;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            ivSoundIcon = (ImageView) itemView.findViewById(R.id.ivSoundIcon);
            llItemContainer = (LinearLayout) itemView.findViewById(R.id.llItemContainer);
        }

    }

    public boolean isHasItems() {
        return hasItems;
    }

    public void setHasItems(boolean hasItems) {
        this.hasItems = hasItems;
    }

    public ListItemAction getListItemAction() {
        return listItemAction;
    }

    public void setListItemAction(ListItemAction listItemAction) {
        this.listItemAction = listItemAction;
    }
}