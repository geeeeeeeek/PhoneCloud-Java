package com.xiao.myyun;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private Button beifen;
	private Button huanyuan;
	private Button tongbu;
	private Button wenjian;
	private Sms sms=new Sms(this);
	private Contact contact=new Contact(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews(); 
 
	}
	private void initViews() {
		beifen=(Button)findViewById(R.id.beifen);
		huanyuan=(Button)findViewById(R.id.huanyuan);
		tongbu=(Button)findViewById(R.id.tongbu);
		wenjian=(Button)findViewById(R.id.wenjian);
		beifen.setOnClickListener(this);
		huanyuan.setOnClickListener(this);
		tongbu.setOnClickListener(this);
		wenjian.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) { 
		
		switch(v.getId()){
		case R.id.beifen: 
			sms.beifen();
			break;
		case R.id.huanyuan: 
			sms.huanyuan();
			break;
		case R.id.tongbu:
			contact.tongbu();
			break;
		case R.id.wenjian:
			Intent in=new Intent(this,FileBakRestoreActivity.class);
			startActivity(in);
		}
	}

      
}
