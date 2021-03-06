package com.dungle1310.personalnotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dung on 1/29/2017.
 */

public class DropboxAdapter extends RecyclerView.Adapter<DropboxAdapter.RecyclerViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mData = Collections.emptyList();

    public DropboxAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.custom_dbx_adapter_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.title.setText(mData.get(position));
    }

    public void setData(List<String> data) {
        this.mData = data;
    }

    public void add(String dirname) {
        mData.add(dirname);
        Collections.sort(mData, String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.drop_box_directory_name);
        }
    }
}
