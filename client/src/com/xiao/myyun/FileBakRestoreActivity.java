package com.xiao.myyun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FileBakRestoreActivity extends Activity implements OnClickListener{
    private Button bakButton;
    private Button restoreButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_bak_restore);
		bakButton=(Button)findViewById(R.id.bakbutton);
		restoreButton=(Button)findViewById(R.id.restorebutton);
		bakButton.setOnClickListener(this);
		restoreButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		 switch(v.getId()){
		 case R.id.bakbutton: 
			 startActivity(new Intent(this,FSExplorer.class));
			 break;
		 case R.id.restorebutton:
			 startActivity(new Intent(this,FileRestore.class));
			 break;
		 }
		
	}
	
	

	
}
