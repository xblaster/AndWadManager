package net.lo2k;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import models.Operation;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return data;
    }
    
	protected void request() {
		//RestClient client = new RestClient("http://wadmanager.lo2k.net/remote/bank/1");
		//RestClient client = new RestClient("http://www.lo2k.net");
		//client.AddParam("accountType", "GOOGLE");
		//client.AddParam("source", "tboda-widgalytics-0.1");
		//client.AddParam("Email", _username);
		//client.AddParam("Passwd", _password);
		//client.AddParam("service", "analytics");
		//client.AddHeader("GData-Version", "2");

		/*try {
		    client.Execute(RequestMethod.POST);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		String response = client.getResponse();*/
		Reader r = new InputStreamReader(getJSONData("http://wadmanager.lo2k.net/remote/bank/1"));
		Log.v("debug","oui ok");
		Log.v("DEBUG", r.toString());
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Operation>>() {}.getType();
		List<Operation> operations = gson.fromJson(r, listType);
		
		Log.v("DEBUG",""+operations.size());
	}
}