package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class DoctorDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明医生编号控件
	private TextView TV_doctorNo;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明所在科室控件
	private TextView TV_departmentObj;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明医生照片图片框
	private ImageView iv_doctorPhoto;
	// 声明学历控件
	private TextView TV_education;
	// 声明入院日期控件
	private TextView TV_inDate;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明每日出诊次数控件
	private TextView TV_visiteTimes;
	// 声明附加信息控件
	private TextView TV_memo;
	/* 要保存的医生信息 */
	Doctor doctor = new Doctor(); 
	/* 医生管理业务逻辑层 */
	private DoctorService doctorService = new DoctorService();
	private DepartmentService departmentService = new DepartmentService();
	private String doctorNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.doctor_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看医生详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_doctorNo = (TextView) findViewById(R.id.TV_doctorNo);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_departmentObj = (TextView) findViewById(R.id.TV_departmentObj);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		iv_doctorPhoto = (ImageView) findViewById(R.id.iv_doctorPhoto); 
		TV_education = (TextView) findViewById(R.id.TV_education);
		TV_inDate = (TextView) findViewById(R.id.TV_inDate);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_visiteTimes = (TextView) findViewById(R.id.TV_visiteTimes);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		doctorNo = extras.getString("doctorNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DoctorDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    doctor = doctorService.GetDoctor(doctorNo); 
		this.TV_doctorNo.setText(doctor.getDoctorNo());
		this.TV_password.setText(doctor.getPassword());
		Department departmentObj = departmentService.GetDepartment(doctor.getDepartmentObj());
		this.TV_departmentObj.setText(departmentObj.getDepartmentName());
		this.TV_name.setText(doctor.getName());
		this.TV_sex.setText(doctor.getSex());
		byte[] doctorPhoto_data = null;
		try {
			// 获取图片数据
			doctorPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + doctor.getDoctorPhoto());
			Bitmap doctorPhoto = BitmapFactory.decodeByteArray(doctorPhoto_data, 0,doctorPhoto_data.length);
			this.iv_doctorPhoto.setImageBitmap(doctorPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_education.setText(doctor.getEducation());
		Date inDate = new Date(doctor.getInDate().getTime());
		String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
		this.TV_inDate.setText(inDateStr);
		this.TV_telephone.setText(doctor.getTelephone());
		this.TV_visiteTimes.setText(doctor.getVisiteTimes() + "");
		this.TV_memo.setText(doctor.getMemo());
	} 
}
