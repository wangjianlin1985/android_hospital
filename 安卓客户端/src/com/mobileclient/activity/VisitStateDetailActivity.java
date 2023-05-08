package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;
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
public class VisitStateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明状态id控件
	private TextView TV_visitStateId;
	// 声明出诊状态控件
	private TextView TV_visitStateName;
	/* 要保存的出诊状态信息 */
	VisitState visitState = new VisitState(); 
	/* 出诊状态管理业务逻辑层 */
	private VisitStateService visitStateService = new VisitStateService();
	private int visitStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.visitstate_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看出诊状态详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_visitStateId = (TextView) findViewById(R.id.TV_visitStateId);
		TV_visitStateName = (TextView) findViewById(R.id.TV_visitStateName);
		Bundle extras = this.getIntent().getExtras();
		visitStateId = extras.getInt("visitStateId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				VisitStateDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    visitState = visitStateService.GetVisitState(visitStateId); 
		this.TV_visitStateId.setText(visitState.getVisitStateId() + "");
		this.TV_visitStateName.setText(visitState.getVisitStateName());
	} 
}
