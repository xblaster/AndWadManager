package net.lo2k;

import net.lo2k.RestClient.RequestMethod;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

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

	protected void addOperation(EditText txtAmount, EditText txtName) {
		RestClient client = new RestClient(
				"http://wadmanager.lo2k.net/remote/bank/add/1");
		client.AddParam("amount", txtAmount.getText().toString());
		client.AddParam("name", txtName.getText().toString());

		try {
			client.Execute(RequestMethod.POST);
		} catch (Exception e) {
			e.printStackTrace();
			Context context = getApplicationContext();
			CharSequence text = "Error to save";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

			return;
		}

		Context context = getApplicationContext();
		CharSequence text = "Operation saved";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		// finish activity
		finish();
	}
}
