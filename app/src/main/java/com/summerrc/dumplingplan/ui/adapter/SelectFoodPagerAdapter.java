package com.summerrc.dumplingplan.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.ui.activity.SelectFoodActivity;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SummerRC on 2015/7/3 0003.
 * 选择食物界面的ViewPager的适配器
 */
public class SelectFoodPagerAdapter extends PagerAdapter{
    private ArrayList<View> list;
    private Activity curActivity;
    private Map<Integer, SoftReference<Bitmap>> imageCache = new HashMap<>();
    private int res[] = {R.mipmap.cabinet_one, R.mipmap.cabinet_two, R.mipmap.cabinet_three, R.mipmap.cabinet_four};

    public  SelectFoodPagerAdapter(ArrayList<View> list, Activity curActivity){
        this.list = list;
        this.curActivity = curActivity;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position % list.size());
        SoftReference softReference = imageCache.get(position % list.size());
         if(softReference == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(curActivity.getResources(), res[position % list.size()]);
            addBitmapToCache(position % list.size(), bitmap);
            view.findViewById(R.id.viewPager_viewContainer).setBackgroundDrawable(new BitmapDrawable(curActivity.getResources(), bitmap));
            ((SelectFoodActivity) curActivity).setListener(position, view);
        } else {
            view.findViewById(R.id.viewPager_viewContainer).setBackgroundDrawable(new BitmapDrawable(curActivity.getResources(), (Bitmap) softReference.get()));
        }
        container.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addBitmapToCache(int key, Bitmap bitmap) {
        SoftReference<Bitmap> softBitmap = new SoftReference<>(bitmap);
        imageCache.put(key, softBitmap);
    }
}
