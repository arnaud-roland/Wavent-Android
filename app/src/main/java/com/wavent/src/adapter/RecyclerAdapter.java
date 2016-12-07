package com.wavent.src.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wavent.R;
import com.wavent.src.holder.EventViewHolder;
import com.wavent.src.holder.LoadingViewHolder;
import com.wavent.src.listener.OnClickReceiverListener;
import com.wavent.src.listener.OnLoadMoreListener;
import com.wavent.src.model.Event;

import java.util.List;

/**
 * Created by arnau on 29/11/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener mOnLoadMoreListener;
    private OnClickReceiverListener onClickReceiverListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private int current_page = 1;

    private int mExpandedPosition = -1;

    List<Event> list;


    public RecyclerAdapter(RecyclerView mRecyclerView, List<Event> list) {
        this.list = list;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    current_page++;
                    if (mOnLoadMoreListener != null) {
                        isLoading = true;
                        mOnLoadMoreListener.onLoadMore(current_page);
                    }
                }
            }
        });
    }



    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_holder_layout,viewGroup,false);
            return new EventViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_holder_layout,viewGroup,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final boolean isExpanded = position==mExpandedPosition;
        if (holder instanceof RecyclerView.ViewHolder) {
            Event event = list.get(position);
            EventViewHolder myViewHolder = (EventViewHolder) holder;
            myViewHolder.bind(myViewHolder.itemView.getContext(),event, onClickReceiverListener);
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setOnClickReceiverListener(OnClickReceiverListener mOnClickListener){
        this.onClickReceiverListener = mOnClickListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}