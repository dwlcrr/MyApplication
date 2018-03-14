package com.example.test.adapter;

import android.content.Context;

import java.util.List;


public abstract class MyBaseAdapter<E> extends android.widget.BaseAdapter {
    protected List<E> list;
    protected  Context context;
    public MyBaseAdapter(List<E> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {

        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
