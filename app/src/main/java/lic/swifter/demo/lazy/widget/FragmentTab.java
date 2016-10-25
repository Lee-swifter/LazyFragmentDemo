package lic.swifter.demo.lazy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lic.swifter.demo.lazy.R;

/**
 * 首页底部的组合控件
 * Created by Li on 2016/10/12.
 */
public class FragmentTab extends LinearLayout {

    private ImageView imageView;

    public FragmentTab(Context context) {
        this(context, null);
    }

    public FragmentTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FragmentTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.widget_fragment_tab, this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FragmentTab);
        boolean selected = ta.getBoolean(R.styleable.FragmentTab_selected, false);
        int textRes = ta.getResourceId(R.styleable.FragmentTab_text, 0);
        ta.recycle();

        imageView = (ImageView) findViewById(R.id.fragment_tab_image);
        TextView textView = (TextView) findViewById(R.id.fragment_tab_text);

        setBackgroundResource(R.drawable.selector_fragment_tab_bg);

        setSelected(selected);
        textView.setText(textRes);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(selected) {
            imageView.setImageResource(R.drawable.icon_tab_checked);
        } else {
            imageView.setImageResource(R.drawable.icon_tab);
        }
    }

}
