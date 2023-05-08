package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class DoctorAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明医生编号输入框
	private EditText ET_doctorNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明所在科室下拉框
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null;
	/*所在科室管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明医生照片图片框控件
	private ImageView iv_doctorPhoto;
	private Button btn_doctorPhoto;
	protected int REQ_CODE_SELECT_IMAGE_doctorPhoto = 1;
	private int REQ_CODE_CAMERA_doctorPhoto = 2;
	// 声明学历输入框
	private EditText ET_education;
	// 出版入院日期控件
	private DatePicker dp_inDate;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明每日出诊次数输入框
	private EditText ET_visiteTimes;
	// 声明附加信息输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的医生信息*/
	Doctor doctor = new Doctor();
	/*医生管理业务逻辑层*/
	private DoctorService doctorService = new DoctorService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.doctor_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加医生");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_doctorNo = (EditText) findViewById(R.id.ET_doctorNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// 获取所有的所在科室
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount];
		for(int i=0;i<departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i).getDepartmentName();
		}
		// 将可选内容与ArrayAdapter连接起来
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// 设置下拉列表的风格
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				doctor.setDepartmentObj(departmentList.get(arg2).getDepartmentId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_departmentObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		iv_doctorPhoto = (ImageView) findViewById(R.id.iv_doctorPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_doctorPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DoctorAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_doctorPhoto);
			}
		});
		btn_doctorPhoto = (Button) findViewById(R.id.btn_doctorPhoto);
		btn_doctorPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_doctorPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_doctorPhoto);  
			}
		});
		ET_education = (EditText) findViewById(R.id.ET_education);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_visiteTimes = (EditText) findViewById(R.id.ET_visiteTimes);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加医生按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取医生编号*/
					if(ET_doctorNo.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "医生编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_doctorNo.setFocusable(true);
						ET_doctorNo.requestFocus();
						return;
					}
					doctor.setDoctorNo(ET_doctorNo.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					doctor.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					doctor.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					doctor.setSex(ET_sex.getText().toString());
					if(doctor.getDoctorPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						DoctorAddActivity.this.setTitle("正在上传图片，稍等...");
						String doctorPhoto = HttpUtil.uploadFile(doctor.getDoctorPhoto());
						DoctorAddActivity.this.setTitle("图片上传完毕！");
						doctor.setDoctorPhoto(doctorPhoto);
					} else {
						doctor.setDoctorPhoto("upload/noimage.jpg");
					}
					/*验证获取学历*/ 
					if(ET_education.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "学历输入不能为空!", Toast.LENGTH_LONG).show();
						ET_education.setFocusable(true);
						ET_education.requestFocus();
						return;	
					}
					doctor.setEducation(ET_education.getText().toString());
					/*获取入院日期*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					doctor.setInDate(new Timestamp(inDate.getTime()));
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					doctor.setTelephone(ET_telephone.getText().toString());
					/*验证获取每日出诊次数*/ 
					if(ET_visiteTimes.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "每日出诊次数输入不能为空!", Toast.LENGTH_LONG).show();
						ET_visiteTimes.setFocusable(true);
						ET_visiteTimes.requestFocus();
						return;	
					}
					doctor.setVisiteTimes(Integer.parseInt(ET_visiteTimes.getText().toString()));
					/*验证获取附加信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					doctor.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传医生信息*/
					DoctorAddActivity.this.setTitle("正在上传医生信息，稍等...");
					String result = doctorService.AddDoctor(doctor);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_doctorPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_doctorPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_doctorPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_doctorPhoto.setImageBitmap(booImageBm);
				this.iv_doctorPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.doctor.setDoctorPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_doctorPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_doctorPhoto.setImageBitmap(bm); 
				this.iv_doctorPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			doctor.setDoctorPhoto(filename); 
		}
	}
}
