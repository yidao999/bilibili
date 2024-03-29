package com.example.bilibilidemo.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bilibilidemo.R;

/**
 * author: 小川
 * Date: 2019/7/23
 * Description:
 */
public class CustomEmptyView extends FrameLayout {
    private ImageView mEmptyImg;
    private TextView mEmptyText;

    public CustomEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomEmptyView(Context context) {
        this(context, null);
    }

    public CustomEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this);
        mEmptyImg = (ImageView) view.findViewById(R.id.empty_img);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
    }

    public void setEmptyImage(int imgRes) {
        mEmptyImg.setImageResource(imgRes);
    }

    public void setEmptyText(String text) {
        mEmptyText.setText(text);
    }
}
