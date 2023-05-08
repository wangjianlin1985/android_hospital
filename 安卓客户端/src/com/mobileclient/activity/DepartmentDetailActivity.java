package com.mobileclient.activity;

import java.util.Date;
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
public class DepartmentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明科室id控件
	private TextView TV_departmentId;
	// 声明科室名称控件
	private TextView TV_departmentName;
	/* 要保存的科室信息 */
	Department department = new Department(); 
	/* 科室管理业务逻辑层 */
	private DepartmentService departmentService = new DepartmentService();
	private int departmentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.department_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看科室详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_departmentId = (TextView) findViewById(R.id.TV_departmentId);
		TV_departmentName = (TextView) findViewById(R.id.TV_departmentName);
		Bundle extras = this.getIntent().getExtras();
		departmentId = extras.getInt("departmentId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DepartmentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    department = departmentService.GetDepartment(departmentId); 
		this.TV_departmentId.setText(department.getDepartmentId() + "");
		this.TV_departmentName.setText(department.getDepartmentName());
	} 
}
