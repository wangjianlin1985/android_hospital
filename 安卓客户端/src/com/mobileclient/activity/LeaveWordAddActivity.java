package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.LeaveWord;
import com.mobileclient.service.LeaveWordService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class LeaveWordAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明标题输入框
	private EditText ET_title;
	// 声明留言内容输入框
	private EditText ET_content;
	// 声明留言时间输入框
	private EditText ET_addTime;
	// 声明留言人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*留言人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明回复内容输入框
	private EditText ET_replyContent;
	// 声明回复时间输入框
	private EditText ET_replyTime;
	// 声明回复的医生输入框
	private EditText ET_replyDoctor;
	protected String carmera_path;
	/*要保存的留言信息*/
	LeaveWord leaveWord = new LeaveWord();
	/*留言管理业务逻辑层*/
	private LeaveWordService leaveWordService = new LeaveWordService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveword_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加留言");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的留言人
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveWord.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_replyContent = (EditText) findViewById(R.id.ET_replyContent);
		ET_replyTime = (EditText) findViewById(R.id.ET_replyTime);
		ET_replyDoctor = (EditText) findViewById(R.id.ET_replyDoctor);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加留言按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(LeaveWordAddActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					leaveWord.setTitle(ET_title.getText().toString());
					/*验证获取留言内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(LeaveWordAddActivity.this, "留言内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					leaveWord.setContent(ET_content.getText().toString());
					/*验证获取留言时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(LeaveWordAddActivity.this, "留言时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					leaveWord.setAddTime(ET_addTime.getText().toString());
					/*验证获取回复内容*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(LeaveWordAddActivity.this, "回复内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					leaveWord.setReplyContent(ET_replyContent.getText().toString());
					/*验证获取回复时间*/ 
					if(ET_replyTime.getText().toString().equals("")) {
						Toast.makeText(LeaveWordAddActivity.this, "回复时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyTime.setFocusable(true);
						ET_replyTime.requestFocus();
						return;	
					}
					leaveWord.setReplyTime(ET_replyTime.getText().toString());
					/*验证获取回复的医生*/ 
					if(ET_replyDoctor.getText().toString().equals("")) {
						Toast.makeText(LeaveWordAddActivity.this, "回复的医生输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyDoctor.setFocusable(true);
						ET_replyDoctor.requestFocus();
						return;	
					}
					leaveWord.setReplyDoctor(ET_replyDoctor.getText().toString());
					/*调用业务逻辑层上传留言信息*/
					LeaveWordAddActivity.this.setTitle("正在上传留言信息，稍等...");
					String result = leaveWordService.AddLeaveWord(leaveWord);
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
	}
}
