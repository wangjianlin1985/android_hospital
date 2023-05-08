package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.domain.TimeSlot;
import com.mobileclient.service.TimeSlotService;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明预约idTextView
	private TextView TV_orderId;
	// 声明预约用户下拉框
	private Spinner spinner_uesrObj;
	private ArrayAdapter<String> uesrObj_adapter;
	private static  String[] uesrObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*预约用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明预约医生下拉框
	private Spinner spinner_doctor;
	private ArrayAdapter<String> doctor_adapter;
	private static  String[] doctor_ShowText  = null;
	private List<Doctor> doctorList = null;
	/*预约医生管理业务逻辑层*/
	private DoctorService doctorService = new DoctorService();
	// 出版预约日期控件
	private DatePicker dp_orderDate;
	// 声明预约时间段下拉框
	private Spinner spinner_timeSlotObj;
	private ArrayAdapter<String> timeSlotObj_adapter;
	private static  String[] timeSlotObj_ShowText  = null;
	private List<TimeSlot> timeSlotList = null;
	/*预约时间段管理业务逻辑层*/
	private TimeSlotService timeSlotService = new TimeSlotService();
	// 声明出诊状态下拉框
	private Spinner spinner_visiteStateObj;
	private ArrayAdapter<String> visiteStateObj_adapter;
	private static  String[] visiteStateObj_ShowText  = null;
	private List<VisitState> visitStateList = null;
	/*出诊状态管理业务逻辑层*/
	private VisitStateService visitStateService = new VisitStateService();
	// 声明医生说明输入框
	private EditText ET_introduce;
	protected String carmera_path;
	/*要保存的预约信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*预约管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑预约信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		spinner_uesrObj = (Spinner) findViewById(R.id.Spinner_uesrObj);
		// 获取所有的预约用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		uesrObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			uesrObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		uesrObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, uesrObj_ShowText);
		// 设置图书类别下拉列表的风格
		uesrObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_uesrObj.setAdapter(uesrObj_adapter);
		// 添加事件Spinner事件监听
		spinner_uesrObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setUesrObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_uesrObj.setVisibility(View.VISIBLE);
		spinner_doctor = (Spinner) findViewById(R.id.Spinner_doctor);
		// 获取所有的预约医生
		try {
			doctorList = doctorService.QueryDoctor(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int doctorCount = doctorList.size();
		doctor_ShowText = new String[doctorCount];
		for(int i=0;i<doctorCount;i++) { 
			doctor_ShowText[i] = doctorList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		doctor_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, doctor_ShowText);
		// 设置图书类别下拉列表的风格
		doctor_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_doctor.setAdapter(doctor_adapter);
		// 添加事件Spinner事件监听
		spinner_doctor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setDoctor(doctorList.get(arg2).getDoctorNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_doctor.setVisibility(View.VISIBLE);
		dp_orderDate = (DatePicker)this.findViewById(R.id.dp_orderDate);
		spinner_timeSlotObj = (Spinner) findViewById(R.id.Spinner_timeSlotObj);
		// 获取所有的预约时间段
		try {
			timeSlotList = timeSlotService.QueryTimeSlot(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int timeSlotCount = timeSlotList.size();
		timeSlotObj_ShowText = new String[timeSlotCount];
		for(int i=0;i<timeSlotCount;i++) { 
			timeSlotObj_ShowText[i] = timeSlotList.get(i).getTimeSlotName();
		}
		// 将可选内容与ArrayAdapter连接起来
		timeSlotObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSlotObj_ShowText);
		// 设置图书类别下拉列表的风格
		timeSlotObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_timeSlotObj.setAdapter(timeSlotObj_adapter);
		// 添加事件Spinner事件监听
		spinner_timeSlotObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setTimeSlotObj(timeSlotList.get(arg2).getTimeSlotId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_timeSlotObj.setVisibility(View.VISIBLE);
		spinner_visiteStateObj = (Spinner) findViewById(R.id.Spinner_visiteStateObj);
		// 获取所有的出诊状态
		try {
			visitStateList = visitStateService.QueryVisitState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int visitStateCount = visitStateList.size();
		visiteStateObj_ShowText = new String[visitStateCount];
		for(int i=0;i<visitStateCount;i++) { 
			visiteStateObj_ShowText[i] = visitStateList.get(i).getVisitStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		visiteStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, visiteStateObj_ShowText);
		// 设置图书类别下拉列表的风格
		visiteStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_visiteStateObj.setAdapter(visiteStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_visiteStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setVisiteStateObj(visitStateList.get(arg2).getVisitStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_visiteStateObj.setVisibility(View.VISIBLE);
		ET_introduce = (EditText) findViewById(R.id.ET_introduce);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		/*单击修改预约按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取出版日期*/
					Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
					orderInfo.setOrderDate(new Timestamp(orderDate.getTime()));
					/*验证获取医生说明*/ 
					if(ET_introduce.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "医生说明输入不能为空!", Toast.LENGTH_LONG).show();
						ET_introduce.setFocusable(true);
						ET_introduce.requestFocus();
						return;	
					}
					orderInfo.setIntroduce(ET_introduce.getText().toString());
					/*调用业务逻辑层上传预约信息*/
					OrderInfoEditActivity.this.setTitle("正在更新预约信息，稍等...");
					String result = orderInfoService.UpdateOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderId);
		this.TV_orderId.setText(orderId+"");
		for (int i = 0; i < userInfoList.size(); i++) {
			if (orderInfo.getUesrObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_uesrObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < doctorList.size(); i++) {
			if (orderInfo.getDoctor().equals(doctorList.get(i).getDoctorNo())) {
				this.spinner_doctor.setSelection(i);
				break;
			}
		}
		Date orderDate = new Date(orderInfo.getOrderDate().getTime());
		this.dp_orderDate.init(orderDate.getYear() + 1900,orderDate.getMonth(), orderDate.getDate(), null);
		for (int i = 0; i < timeSlotList.size(); i++) {
			if (orderInfo.getTimeSlotObj() == timeSlotList.get(i).getTimeSlotId()) {
				this.spinner_timeSlotObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < visitStateList.size(); i++) {
			if (orderInfo.getVisiteStateObj() == visitStateList.get(i).getVisitStateId()) {
				this.spinner_visiteStateObj.setSelection(i);
				break;
			}
		}
		this.ET_introduce.setText(orderInfo.getIntroduce());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
