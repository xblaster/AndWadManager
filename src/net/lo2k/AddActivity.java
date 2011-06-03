package net.lo2k;

import net.lo2k.RestClient.RequestMethod;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

		Button btn = (Button) findViewById(R.id.saveBtn);

		final EditText txtAmount = (EditText) findViewById(R.id.addoperationAmount);
		final EditText txtName = (EditText) findViewById(R.id.addoperationDesc);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addOperation(txtAmount, txtName);
			}
		});
	}

	protected void addOperation(final EditText txtAmount, final EditText txtName) {

		dialog = ProgressDialog.show(this, "", "Sending data to server", true);
		dialog.show();

		new Thread((new Runnable() {
			@Override
			public void run() {

				try {
					RestClient client = new RestClient(
							"http://wadmanager.lo2k.net/remote/bank/add/1");
					client.AddParam("amount", txtAmount.getText().toString());
					client.AddParam("name", txtName.getText().toString());

					client.Execute(RequestMethod.POST);
					
					onRequestFinishHandler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
					Context context = getApplicationContext();
					CharSequence text = "Error to save\n:"+e.toString();
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

					return;
				}
			}
		})).start();

		
	}
	
	final Handler onRequestFinishHandler = new Handler() {
	    public void handleMessage(Message m) {
	    	dialog.dismiss();
	    	Context context = getApplicationContext();
			CharSequence text = "Operation added";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

			// finish activity
			finish();
	    }
	};
}
