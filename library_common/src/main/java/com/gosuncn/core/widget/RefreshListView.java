package com.gosuncn.core.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gosuncn.core.R;


/**
 * 下拉刷新listview
 * 上拉加载暂未实现
 * @author hzp
 *
 */
public class RefreshListView extends FrameLayout implements OnTouchListener{
	
	private static int id=0;
	/** 
     * 下拉状态 
     */  
    public static final int STATUS_PULL_TO_REFRESH = 0;  
  
    /** 
     * 释放立即刷新状态 
     */  
    public static final int STATUS_RELEASE_TO_REFRESH = 1;  
  
    /** 
     * 正在刷新状态 
     */  
    public static final int STATUS_REFRESHING = 2;  
  
    /** 
     * 刷新完成或未刷新状态 
     */  
    public static final int STATUS_REFRESH_FINISHED = 3;
    
    /**
     * 上拉状态
     */
    public static final int STATUS_PULL_TO_LOAD = 4;
  
    /** 
     * 下拉头部回滚的速度 
     */  
    public static final int SCROLL_SPEED = -20;  
  
    /** 
     * 下拉刷新的回调接口 
     */  
    private PullToRefreshListener mListener;  
 
  
    /** 
     * 下拉头的View 
     */  
    private View header;
    
    /**
     * 上拉view
     */
    private View foot;
  
    /** 
     * 需要去下拉刷新的ListView 
     */  
    private ListView listView;  
  
  
    /** 
     * 指示下拉和释放的箭头 
     */  
    private ImageView arrow;  
  
    /** 
     * 指示下拉和释放的文字描述 
     */  
    private TextView description;  
  
    /**
     * 上拉和释放箭头
     */
    private ImageView loadArrow;
    
    /**
     * 指示上拉和释放文字
     */
    private TextView loadDescription;
    
    /** 
     * 下拉头的布局参数 
     */  
    private MarginLayoutParams headerLayoutParams;
  
    /**
     * 上拉布局参数
     */
    private MarginLayoutParams footLayoutParams;
    
    /** 
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分 
     */  
    private int mId = -1;  
  
    /** 
     * 下拉头的高度 
     */  
    private int hideHeaderHeight;  
    
    /**
     * 上拉高度
     */
    private int hideFootHeight;
    
    /** 
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH, 
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED 
     */  
    private int currentStatus = STATUS_REFRESH_FINISHED;;  
  
    /** 
     * 记录上一次的状态是什么，避免进行重复操作 
     */  
    private int lastStatus = currentStatus;  
  
    /** 
     * 手指按下时的屏幕纵坐标 
     */  
    private float yDown;  
  
    /** 
     * 在被判定为滚动之前用户手指可以移动的最大值。 
     */  
    private int touchSlop;  
  
    /** 
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次 
     */  
    private boolean loadOnce;  
  
    /** 
     * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉 
     */  
    private boolean ableToPull;  
    
    /**
     * 当前是否可以上拉
     */
    private boolean ableToLoad;

    private boolean isLoading;
    
    private int RefreshModel = 3;
    private Handler handler =new Handler();
    
    /** 
     * 下拉刷新控件的构造函数，会在运行时动态添加一个下拉头的布局。 
     *  
     * @param context 
     * @param attrs 
     */  
    public RefreshListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
//        preferences = PreferenceManager.getDefaultSharedPreferences(context);  
        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
        
        arrow = (ImageView) header.findViewById(R.id.iv_refresh_icon);
        description = (TextView) header.findViewById(R.id.tv_refresh_hint);
        
        foot = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null,true);
        loadArrow = (ImageView) foot.findViewById(R.id.iv_loading_icon);
//        loadDescription = (TextView) foot.findViewById(R.id.tv_refresh_hint);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();  
 
//        setOrientation(VERTICAL);
        LinearLayout l = new LinearLayout(context);
        l.setOrientation(LinearLayout.VERTICAL);
        l.addView(header, 0);
        listView = new ListView(context);
        listView.setDivider(null);
        l.addView(listView,1,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        
        addView(l,0);
        
        foot.setVisibility(GONE);
        AnimationDrawable a = (AnimationDrawable) loadArrow.getDrawable();
		a.start();
        addView(foot,1,new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL));
        
//        listView.addFooterView(foot);
        isLoading = false;
    }  
  
    /** 
     * 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件。 
     */  
    @Override  
    protected void onLayout(boolean changed, int l, int t, int r, int b) {  
        super.onLayout(changed, l, t, r, b);  
        if (changed && !loadOnce) {  
            hideHeaderHeight = -header.getHeight();  
            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();  
            headerLayoutParams.topMargin = hideHeaderHeight;
            
//            hideFootHeight = foot.getHeight();
//            footLayoutParams = (MarginLayoutParams) foot.getLayoutParams();
//            footLayoutParams.bottomMargin = hideFootHeight;
            listView.setOnTouchListener(this);
            Log.i("coutn","list : "+listView.getCount());
//            if(listView.getCount()<3)
//            	listView.removeFooterView(foot);
            new HideHeaderTask().execute();
            loadOnce = true;
        }  
    }  
  
    public ListView getListView(){
    	return listView;
    }
    
    /**
     * 设置刷新模式，1为只能下拉刷新，2为只能上拉加载，3为都可以，0为禁止
     * @param Model
     */
    public void setModel(int Model){
    	RefreshModel = Model;
    }
    
    /**
     * 滑动到底部加载更多
     */
    public void setLoadMore(){
    	listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE&&ableToLoad&&!isLoading){
					foot.setVisibility(VISIBLE);
					isLoading = true;
					mListener.onLoad();
//					listView.addFooterView(foot);
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    /** 
     * 当ListView被触摸时调用，其中处理了各种下拉刷新的具体逻辑。 
     */  
    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
        setIsAbleToPull(event);
        setIsAbleToLoad(event);
        if (ableToPull&&!isLoading) {  
            switch (event.getAction()) {  
            case MotionEvent.ACTION_DOWN:  
                yDown = event.getRawY();  
                break;  
            case MotionEvent.ACTION_MOVE:  
                float yMove = event.getRawY();  
                int distance = (int) (yMove - yDown);  
                // 如果手指是下滑状态，并且下拉头是完全隐藏的，就屏蔽下拉事件  
                if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {  
                    return false;  
                }
                if(currentStatus==STATUS_REFRESHING&&headerLayoutParams.topMargin <= hideHeaderHeight){
                	finishRefreshing(false);
                	return false;
                }
                if (distance < touchSlop) {  
                    return false;  
                }  
                if (currentStatus != STATUS_REFRESHING) {  
                    if (headerLayoutParams.topMargin > 0) {  
                        currentStatus = STATUS_RELEASE_TO_REFRESH;  
                    } else {  
                        currentStatus = STATUS_PULL_TO_REFRESH;  
                    }  
                    // 通过偏移下拉头的topMargin值，来实现下拉效果  
                    headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;  
                    header.setLayoutParams(headerLayoutParams);  
                }  
                break;  
            case MotionEvent.ACTION_UP:  
            default:  
                if (currentStatus == STATUS_RELEASE_TO_REFRESH) {  
                    // 松手时如果是释放立即刷新状态，就去调用正在刷新的任务  
                    new RefreshingTask().execute();  
                } else if (currentStatus == STATUS_PULL_TO_REFRESH) {  
                    // 松手时如果是下拉状态，就去调用隐藏下拉头的任务  
                    new HideHeaderTask().execute();  
                }  
                break;  
            }  
            // 时刻记得更新下拉头中的信息  
            if (currentStatus == STATUS_PULL_TO_REFRESH  
                    || currentStatus == STATUS_RELEASE_TO_REFRESH) {  
                updateHeaderView();  
                // 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态  
                listView.setPressed(false);  
                listView.setFocusable(false);  
                listView.setFocusableInTouchMode(false);  
                lastStatus = currentStatus;  
                // 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件  
                return true;  
            }  
        }
//        if(ableToLoad){
//        	Log.i("load","loadMore");
////        	mListener.onLoad(); 
//        }
        return false;  
    }  
  
    /** 
     * 给下拉刷新控件注册一个监听器。 
     *  
     * @param listener 
     *            监听器的实现。 
     */
    public void setOnRefreshListener(PullToRefreshListener listener) {  
        mListener = listener;  
        mId = id++;  
    }  
  
    /** 
     * 当所有的刷新逻辑完成后，记录调用一下，否则你的ListView将一直处于正在刷新状态。 
     */  
    public void finishRefreshing(boolean success) {
    	if(isLoading){
//    		listView.removeFooterView(foot);
    		foot.setVisibility(GONE);
    		isLoading = false;
    	}
        currentStatus = STATUS_REFRESH_FINISHED;
        if(success){
        	arrow.clearAnimation();
        	arrow.setImageResource(R.drawable.frag_msg_alarm_checkbox_on);
//        	arrow.setVisibility(GONE);
//        	Drawable left = getResources().getDrawable(R.drawable.frag_msg_alarm_checkbox_on);
//        	left.setBounds(0,0,left.getMinimumWidth(),left.getMinimumHeight());
//        	description.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
//        	description.setCompoundDrawablePadding(20);
        	description.setText("刷新成功");
        }
        new HideHeaderTask().execute();
    }  
  
    /** 
     * 根据当前ListView的滚动状态来设定 {@link #ableToPull} 
     * 的值，每次都需要在onTouch中第一个执行，这样可以判断出当前应该是滚动ListView，还是应该进行下拉。 
     *  
     * @param event 
     */  
    private void setIsAbleToPull(MotionEvent event) {
    	if(RefreshModel!=1&&RefreshModel!=3){
    		ableToPull = false;
    		return;
    	}
        View firstChild = listView.getChildAt(0);  
        if (firstChild != null) {  
            int firstVisiblePos = listView.getFirstVisiblePosition();  
            if (firstVisiblePos == 0 && firstChild.getTop() == 0) {  
                if (!ableToPull) {  
                    yDown = event.getRawY();  
                }  
                // 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新  
                ableToPull = true;  
            } else {  
                if (headerLayoutParams.topMargin != hideHeaderHeight) {  
                    headerLayoutParams.topMargin = hideHeaderHeight;  
                    header.setLayoutParams(headerLayoutParams);  
                }  
                ableToPull = false;  
            }  
        } else {  
            // 如果ListView中没有元素，也应该允许下拉刷新  
            ableToPull = true;  
        }  
    }
    
    private void setIsAbleToLoad(MotionEvent event){
    	if(RefreshModel!=2&&RefreshModel!=3){
    		ableToLoad = false;
    		return;
    	}
        if (listView.getCount() >0) {  
            if (listView.getLastVisiblePosition() == listView.getCount()-1&&listView.getChildAt(listView.getChildCount()-1).getBottom()<=listView.getHeight()) {

                if (!ableToLoad) {  
                    yDown = event.getRawY();  
                }  
                // 如果最后元素的下边缘，距离父布局值为0，就说明ListView滚动到了最底部，此时应该允许上拉加载  
                ableToLoad = true;  
            } else {
                ableToLoad = false;  
            }  
        } else {  
            // 如果ListView中没有元素，不许上拉加载  
            ableToLoad = false;  
        }  
    }
  
    /** 
     * 更新下拉头中的信息。 
     */  
    private void updateHeaderView() {  
        if (lastStatus != currentStatus) {  
            if (currentStatus == STATUS_PULL_TO_REFRESH) {
//            	arrow.setVisibility(VISIBLE);
//            	description.setCompoundDrawables(null, null, null, null);
                description.setText("下拉刷新");  
                arrow.setImageResource(R.drawable.refresh_hint);
                rotateArrow();  
            } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {  
                description.setText("松开刷新");
                arrow.setImageResource(R.drawable.refresh_hint);
                rotateArrow();  
            } else if (currentStatus == STATUS_REFRESHING) {  
                description.setText("刷新中");  
                arrow.clearAnimation();  
                arrow.setImageResource(+R.anim.progress_round);
                final AnimationDrawable a = (AnimationDrawable)arrow.getDrawable();
                a.start();
          
            }  
        }  
    }
    
    private void updateFootView(){
    	if(lastStatus!=currentStatus){
    		if(currentStatus==STATUS_PULL_TO_LOAD){
    			loadDescription.setText("上拉加载");
    			rotateArrow();
                loadArrow.setImageResource(R.drawable.refresh_hint);
    		}else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {  
                description.setText("松开加载");
                rotateArrow();  
            } else if (currentStatus == STATUS_REFRESHING) {  
            	loadDescription.setText("加载中");  
                loadArrow.clearAnimation();  
                loadArrow.setImageResource(+R.anim.progress_round);
                 AnimationDrawable a = (AnimationDrawable)loadArrow.getDrawable();
                a.start();
               
              
            }  
    	}
    }
  
    /** 
     * 根据当前的状态来旋转箭头。 
     */  
    private void rotateArrow() {  
        float pivotX = arrow.getWidth() / 2f;  
        float pivotY = arrow.getHeight() / 2f;  
        float fromDegrees = 0f;  
        float toDegrees = 0f;  
        if (currentStatus == STATUS_PULL_TO_REFRESH) {  
            fromDegrees = 180f;  
            toDegrees = 360f;  
        } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {  
            fromDegrees = 0f;  
            toDegrees = 180f;  
        }  
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);  
        animation.setDuration(100);  
        animation.setFillAfter(true);  
        arrow.startAnimation(animation);  
    }  
  
    /** 
     * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器。 
     *  
     * @author hzp 
     */  
    class RefreshingTask extends AsyncTask<Void, Integer, Void> {  
  
        @Override  
        protected Void doInBackground(Void... params) {
            int topMargin = headerLayoutParams.topMargin;  
            while (true) {  
                topMargin = topMargin + SCROLL_SPEED;  
                if (topMargin <= 0) {  
                    topMargin = 0;  
                    break;  
                }  
                publishProgress(topMargin);
                sleep(10);
            }  
            currentStatus = STATUS_REFRESHING;  
            publishProgress(0);  
            if (mListener != null) {  
                mListener.onRefresh();  
            }  
            return null;  
        }  
  
        @Override  
        protected void onProgressUpdate(Integer... topMargin) {  
            updateHeaderView();  
            headerLayoutParams.topMargin = topMargin[0];  
            header.setLayoutParams(headerLayoutParams);  
        }  
  
    }  
  
    /** 
     * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。 
     *  
     * @author hzp 
     */  
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {  
  
        @Override  
        protected Integer doInBackground(Void... params) {
        	if(headerLayoutParams!=null){
            int topMargin = headerLayoutParams.topMargin;
            sleep(400);
            while (true) {  
                topMargin = topMargin + SCROLL_SPEED;  
                if (topMargin <= hideHeaderHeight) {  
                    topMargin = hideHeaderHeight;  
                    break;  
                }
                publishProgress(topMargin);
                sleep(10);
            }  
            return topMargin;
        	}
        	return null;
        }  
  
        @Override  
        protected void onProgressUpdate(Integer... topMargin) {
        	if(topMargin!=null){
            headerLayoutParams.topMargin = topMargin[0];  
            header.setLayoutParams(headerLayoutParams);
        	}
        }  
  
        @Override  
        protected void onPostExecute(Integer topMargin) {
        	if(topMargin!=null){
            headerLayoutParams.topMargin = topMargin;  
            header.setLayoutParams(headerLayoutParams);  
            currentStatus = STATUS_REFRESH_FINISHED;  
        	}
        }  
    }  
  
    /** 
     * 使当前线程睡眠指定的毫秒数。 
     *  
     * @param time 
     *            指定当前线程睡眠多久，以毫秒为单位 
     */  
    private void sleep(int time) {
        try {  
            Thread.sleep(time);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 下拉刷新的监听器，使用下拉刷新的地方应该注册此监听器来获取刷新回调。 
     *  
     * @author guolin 
     */  
    public interface PullToRefreshListener {  
  
        /** 
         * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。 
         */  
        void onRefresh();  
        void onLoad();
    }  
}
