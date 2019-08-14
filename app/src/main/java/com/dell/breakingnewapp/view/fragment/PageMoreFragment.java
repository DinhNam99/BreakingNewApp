package com.dell.breakingnewapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dell.breakingnewapp.R;
import com.dell.breakingnewapp.adapter.MorePageAdapter;
import com.dell.breakingnewapp.model.Page;
import com.dell.breakingnewapp.view.ActivityMore;

import java.util.ArrayList;
import java.util.List;

public class PageMoreFragment extends Fragment {
    List<Page> pageList;
    MorePageAdapter adapter;
    GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_more,container,false);

        pageList = getListData();
        final GridView gridView = (GridView) view.findViewById(R.id.gvMore);
        gridView.setAdapter(new MorePageAdapter(getContext(), pageList));

        // Khi người dùng click vào các GridItem
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getContext(), ActivityMore.class);
                intent.putExtra("LinkPage",pageList.get(position).getLink());
                intent.putExtra("NamePage",pageList.get(position).getNamePage());
                startActivity(intent);
            }
        });

        return view;
    }

    private  List<Page> getListData() {
        List<Page> list = new ArrayList<>();
        Page economy = new Page(R.drawable.economy,"Economy","https://vnexpress.net/rss/kinh-doanh.rss");
        Page sport = new Page(R.drawable.law,"Law","https://vnexpress.net/rss/phap-luat.rss");
        Page startup = new Page(R.drawable.startup,"StartUp","https://vnexpress.net/rss/startup.rss");
        Page travel = new Page(R.drawable.travel,"Travel","https://vnexpress.net/rss/du-lich.rss");
        Page science = new Page(R.drawable.science,"Science","https://vnexpress.net/rss/khoa-hoc.rss");
        Page car = new Page(R.drawable.car,"Vehicles","https://vnexpress.net/rss/oto-xe-may.rss");
        Page healthy = new Page(R.drawable.heartbeat,"Healthy","https://vnexpress.net/rss/suc-khoe.rss");
        Page education = new Page(R.drawable.education,"Education","https://vnexpress.net/rss/giao-duc.rss");
        list.add(car);
        list.add(economy);
        list.add(science);
        list.add(startup);
        list.add(travel);
        list.add(sport);
        list.add(education);
        list.add(healthy);

        return list;
    }
}
