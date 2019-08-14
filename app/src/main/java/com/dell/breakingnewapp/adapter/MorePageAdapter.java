package com.dell.breakingnewapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dell.breakingnewapp.R;
import com.dell.breakingnewapp.model.Page;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MorePageAdapter extends BaseAdapter {
    private List<Page> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MorePageAdapter(Context aContext,  List<Page> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_more, null);
            holder = new ViewHolder();
            holder.ivMore = (ImageView) convertView.findViewById(R.id.ivMore);
            holder.tvMore = (TextView) convertView.findViewById(R.id.tvMore);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Page page = this.listData.get(position);
        holder.tvMore.setText("" + page.getNamePage());

        Picasso.with(context).load(page.getImage()).into(holder.ivMore);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivMore;
        TextView tvMore;
    }
}
