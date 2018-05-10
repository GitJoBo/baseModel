package com.lq.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lq.banner.adapter.BannerPageAdapter;
import com.lq.banner.adapter.BaseBannerAdapter;
import com.lq.banner.listen.OnItemClickListener;
import com.lq.banner.view.BannerLoopViewPager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.action;


/**
 * 页面翻转控件，极方便的广告栏
 * 支持无限循环，自动翻页，翻页特效
 * @author Sai 支持自动翻页
 */
public class Banner<T> extends FrameLayout implements ViewPager.OnPageChangeListener {
    private int[] page_indicatorId={R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused};
    public static final int INDICATOR_DEFAULT=0;
    public static final int INDICATOR_CIRCLE=1;
    private ArrayList<ImageView> mPointViews;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private BannerPageAdapter pageAdapter;
    private BannerLoopViewPager viewPager;
    private ViewPagerScroller scroller;
    private LinearLayout loPageTurningPoint;
    private long autoTurningTime=2000;
    private boolean turning;
    private boolean canTurn = false;
    private boolean manualPageable = true;
    private boolean isLoop = true;
    private int mIndicatorType;
    private int mPointRadiu;
    private TextView tvCricle;
    private int mBannerCount;
    private boolean isAotuLoop;

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public void setmPointRadiu(int mPointRadiu) {
        this.mPointRadiu = mPointRadiu;
    }

    public void setmIndicatorType(int mIndicatorType) {
        this.mIndicatorType = mIndicatorType;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }
    public void setPageIndicator(int[] indicator){
        page_indicatorId=indicator;
    }

    @Override
    public void onPageSelected(int position) {
        int item = viewPager.getRealItem();
        if(mIndicatorType==INDICATOR_DEFAULT){
            for (int i = 0; i < mPointViews.size(); i++) {
                mPointViews.get(item).setImageResource(page_indicatorId[1]);
                if (item != i) {
                    mPointViews.get(i).setImageResource(page_indicatorId[0]);
                }
            }
        }else if(mIndicatorType==INDICATOR_CIRCLE){
            tvCricle.setText((item+1)+"/"+mBannerCount);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public enum PageIndicatorAlign{
        ALIGN_PARENT_LEFT,ALIGN_PARENT_RIGHT,CENTER_HORIZONTAL
    }
    private AdSwitchTask mSwitchTask;

    public Banner(Context context) {
        super(context);
        init(context);
    }
    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Banner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        isLoop = a.getBoolean(R.styleable.Banner_isLoop,true);
        mIndicatorType = a.getInt(R.styleable.Banner_indicator_type, INDICATOR_DEFAULT);
        mPointRadiu = a.getInt(R.styleable.Banner_indicator_type,dpToPx(5));
        isAotuLoop = a.getBoolean(R.styleable.Banner_isAotuLoop, true);
        a.recycle();
        init(context);
    }
    private void init(Context context) {
        viewPager = getBannerLoopViewPager(context);
        addView(viewPager,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (isAotuLoop){
            initViewPagerScroll();
            mSwitchTask = new AdSwitchTask(this);
        }

    }

    @NonNull
    protected BannerLoopViewPager getBannerLoopViewPager(Context context) {
        return new BannerLoopViewPager(context);
    }


    static class AdSwitchTask implements Runnable {

        private  WeakReference<Banner> reference;

        AdSwitchTask(Banner Banner) {
            this.reference = new WeakReference<>(Banner);
        }

        @Override
        public void run() {
            Banner Banner = reference.get();

            if(Banner != null){
                if (Banner.viewPager != null && Banner.turning) {
                    int page = Banner.viewPager.getCurrentItem() + 1;
                    Banner.viewPager.setCurrentItem(page);
                    Banner.postDelayed(Banner.mSwitchTask, Banner.autoTurningTime);
                }
            }
        }
        public void clear(){
            if(reference!=null){
                reference.clear();
                reference=null;
            }
        }
    }

    public Banner setAdapter(BaseBannerAdapter holderCreator, List<T> datas){
        this.mBannerCount = datas.size();
//        if(pageAdapter!=null)return this;//重新设置datas时要删除
        pageAdapter = new BannerPageAdapter(holderCreator,datas);
        viewPager.setAdapter(pageAdapter,isLoop);
        setPageIndicator();
        return this;
    }
    public Banner setAdapter(BaseBannerAdapter holderCreator, T[] datas){
        List<T> list = Arrays.asList(datas);
        setAdapter(holderCreator,list);
        return this;
    }
    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged(){
        viewPager.getAdapter().notifyDataSetChanged();

        setPageIndicator();
    }

    /**
     * 设置底部指示器是否可见
     * @param visible
     */
    public Banner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    public Banner setPageIndicator() {
        if(mBannerCount==0)return this;
        if(mIndicatorType==INDICATOR_DEFAULT){
            initDefaultIndicator();
        }else {
            initCricleIndicator();
        }
        viewPager.addOnPageChangeListener(this);
        return this;
    }

    private void initCricleIndicator() {
        if(tvCricle!=null)return;
        tvCricle = new TextView(getContext());
        tvCricle.setBackgroundResource(R.drawable.black_background);
        tvCricle.setTextColor(Color.WHITE);
        tvCricle.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(dpToPx(40),dpToPx(40));
        params.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        params.bottomMargin=dpToPx(8);
        params.rightMargin=dpToPx(8);
        tvCricle.setText((viewPager.getRealItem()+1)+"/"+mBannerCount);
        addView(tvCricle,params);
    }

    private void initDefaultIndicator() {
        if(mPointViews==null)
        mPointViews = new ArrayList<>();
        if(loPageTurningPoint==null){
            loPageTurningPoint = new LinearLayout(getContext());
            loPageTurningPoint.setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity=Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
            params.setMargins(dpToPx(5),dpToPx(10),dpToPx(5),dpToPx(10));//控制点的位置
            //params.setMargins(dpToPx(50),dpToPx(60),dpToPx(50),dpToPx(60));
            addView(loPageTurningPoint,params);
        }

        loPageTurningPoint.removeAllViews();
        mPointViews.clear();

        for (int count = 0; count < mBannerCount; count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(mPointRadiu, 0, mPointRadiu, 0);
            if (mPointViews.isEmpty())
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
//            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(dpToPx(5),0,dpToPx(5),0);

            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pointView.getLayoutParams();
//            params.leftMargin=dpToPx(3);
//            params.rightMargin=dpToPx(3);
//            params.setMargins(dpToPx(5),0,dpToPx(5),0);
//            pointView.setLayoutParams(params);
        }
    }

    /**
     * 指示器的方向
     * @param align  三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public Banner setPageIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) loPageTurningPoint.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public Banner startTurning(long autoTurningTime) {
        if(mSwitchTask==null)return this;
        if(turning)return this;
        //如果是正在翻页的话先停掉
        //设置可以翻页并开启翻页
        removeCallbacks(mSwitchTask);
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(mSwitchTask, autoTurningTime);
        return this;
    }
    public Banner startTurning() {
        if(turning){
            return this;
        }
        if(mSwitchTask==null)return this;
        //如果是正在翻页的话先停掉

        //设置可以翻页并开启翻页
        canTurn = true;
        turning = true;
        removeCallbacks(mSwitchTask);
        postDelayed(mSwitchTask, autoTurningTime);
        return this;
    }

    public void stopTurning() {
        if(!turning)return;
        if(mSwitchTask==null)return;

        turning = false;
        removeCallbacks(mSwitchTask);
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public Banner setPageTransformer(PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }


    /**
     * 设置ViewPager的滑动速度
     * */
    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return viewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        viewPager.setCanScroll(manualPageable);
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isAotuLoop)return super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP||action == MotionEvent.ACTION_CANCEL||action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn)startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn)stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    //获取当前的页面index
    public int getCurrentItem(){
        if (viewPager!=null) {
            return viewPager.getRealItem();
        }
        return -1;
    }
    //设置当前的页面index
    public void setcurrentitem(int index){
        if (viewPager!=null) {
            viewPager.setCurrentItem(index);
        }
    }
    //设置当前的页面index
    public void setOffscreenPageLimit(int num){
        if (viewPager!=null) {
            viewPager.setOffscreenPageLimit(num);
        }
    }

//    public void delCurrentItem(int index){
//        if (viewPager != null){
//
//        }
//    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     * @param onPageChangeListener
     * @return
     */
    public Banner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if(onPageChangeListener!=null)
        viewPager.addOnPageChangeListener(onPageChangeListener);
        return this;
    }

    public boolean isLoop() {
        return viewPager.isCanLoop();
    }

    /**
     * 监听item点击
     * @param onItemClickListener
     */
    public Banner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            viewPager.setOnItemClickListener(null);
            return this;
        }
        viewPager.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 设置ViewPager的滚动速度
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration){
        scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

    public BannerLoopViewPager getViewPager() {
        return viewPager;
    }

    public void setisLoop(boolean isLoop) {
        this.isLoop = isLoop;
        viewPager.setCanLoop(isLoop);
    }
    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }


}
