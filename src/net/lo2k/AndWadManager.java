package net.lo2k;

import net.lo2k.RestClient.RequestMethod;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AndWadManager extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				request();
				
			}
		});
    }

	protected void request() {
		RestClient client = new RestClient("http://www.lo2k.net");
		client.AddParam("accountType", "GOOGLE");
		client.AddParam("source", "tboda-widgalytics-0.1");
		//client.AddParam("Email", _username);
		//client.AddParam("Passwd", _password);
		client.AddParam("service", "analytics");
		client.AddHeader("GData-Version", "2");

		try {
		    client.Execute(RequestMethod.POST);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		String response = client.getResponse();
	}
}