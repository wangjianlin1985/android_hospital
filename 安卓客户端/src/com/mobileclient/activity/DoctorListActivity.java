package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.DoctorSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DoctorListActivity extends Activity {
	DoctorSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String doctorNo;
	/* 医生操作业务逻辑层对象 */
	DoctorService doctorService = new DoctorService();
	/*保存查询参数条件的医生对象*/
	private Doctor queryConditionDoctor;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.doctor_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, DoctorQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("医生查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, DoctorAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionDoctor = (Doctor)extras.getSerializable("queryConditionDoctor");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionDoctor = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new DoctorSimpleAdapter(DoctorListActivity.this, list,
	        					R.layout.doctor_list_item,
	        					new String[] { "doctorNo","departmentObj","name","sex","doctorPhoto","education","inDate","visiteTimes" },
	        					new int[] { R.id.tv_doctorNo,R.id.tv_departmentObj,R.id.tv_name,R.id.tv_sex,R.id.iv_doctorPhoto,R.id.tv_education,R.id.tv_inDate,R.id.tv_visiteTimes,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(doctorListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String doctorNo = list.get(arg2).get("doctorNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(DoctorListActivity.this, DoctorDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("doctorNo", doctorNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener doctorListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑医生信息"); 
			menu.add(0, 1, 0, "删除医生信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑医生信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取医生编号
			doctorNo = list.get(position).get("doctorNo").toString();
			Intent intent = new Intent();
			intent.setClass(DoctorListActivity.this, DoctorEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("doctorNo", doctorNo);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除医生信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取医生编号
			doctorNo = list.get(position).get("doctorNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(DoctorListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = doctorService.DeleteDoctor(doctorNo);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询医生信息 */
			List<Doctor> doctorList = doctorService.QueryDoctor(queryConditionDoctor);
			for (int i = 0; i < doctorList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("doctorNo", doctorList.get(i).getDoctorNo());
				map.put("departmentObj", doctorList.get(i).getDepartmentObj());
				map.put("name", doctorList.get(i).getName());
				map.put("sex", doctorList.get(i).getSex());
				/*byte[] doctorPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ doctorList.get(i).getDoctorPhoto());// 获取图片数据
				BitmapFactory.Options doctorPhoto_opts = new BitmapFactory.Options();  
				doctorPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(doctorPhoto_data, 0, doctorPhoto_data.length, doctorPhoto_opts); 
				doctorPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(doctorPhoto_opts, -1, 100*100); 
				doctorPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap doctorPhoto = BitmapFactory.decodeByteArray(doctorPhoto_data, 0, doctorPhoto_data.length, doctorPhoto_opts);
					map.put("doctorPhoto", doctorPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("doctorPhoto", HttpUtil.BASE_URL+ doctorList.get(i).getDoctorPhoto());
				map.put("education", doctorList.get(i).getEducation());
				map.put("inDate", doctorList.get(i).getInDate());
				map.put("visiteTimes", doctorList.get(i).getVisiteTimes());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
