/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.test.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.test.R;
import com.example.test.entity.base.GankModel;
import java.util.List;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/8/17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NewsAdapter extends BaseQuickAdapter<GankModel,BaseViewHolder> {

    public NewsAdapter(List<GankModel> data) {
        super(R.layout.item_main_list, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final GankModel model) {
//        baseViewHolder.setText(R.id.title, model.desc)//
//                .setText(R.id.desc, model.desc)//
//                .setText(R.id.pubDate, model.publishedAt.toString())//
//                .setText(R.id.source, model.source);
//
//        View view = baseViewHolder.getConvertView();
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WebActivity.runActivity(mContext, model.desc, model.url);
//            }
//        });

    }
}
