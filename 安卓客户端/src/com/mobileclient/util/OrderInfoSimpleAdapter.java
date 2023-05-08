package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.DoctorService;
import com.mobileclient.service.TimeSlotService;
import com.mobileclient.service.VisitStateService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_uesrObj = (TextView)convertView.findViewById(R.id.tv_uesrObj);
	  holder.tv_doctor = (TextView)convertView.findViewById(R.id.tv_doctor);
	  holder.tv_orderDate = (TextView)convertView.findViewById(R.id.tv_orderDate);
	  holder.tv_timeSlotObj = (TextView)convertView.findViewById(R.id.tv_timeSlotObj);
	  holder.tv_visiteStateObj = (TextView)convertView.findViewById(R.id.tv_visiteStateObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_uesrObj.setText("预约用户：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("uesrObj").toString()).getName());
	  holder.tv_doctor.setText("预约医生：" + (new DoctorService()).GetDoctor(mData.get(position).get("doctor").toString()).getName());
	  try {holder.tv_orderDate.setText("预约日期：" + mData.get(position).get("orderDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_timeSlotObj.setText("预约时间段：" + (new TimeSlotService()).GetTimeSlot(Integer.parseInt(mData.get(position).get("timeSlotObj").toString())).getTimeSlotName());
	  holder.tv_visiteStateObj.setText("出诊状态：" + (new VisitStateService()).GetVisitState(Integer.parseInt(mData.get(position).get("visiteStateObj").toString())).getVisitStateName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_uesrObj;
    	TextView tv_doctor;
    	TextView tv_orderDate;
    	TextView tv_timeSlotObj;
    	TextView tv_visiteStateObj;
    }
} 
