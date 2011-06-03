package net.lo2k;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import models.Operation;
import net.lo2k.RestClient.RequestMethod;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class ViewDetailsActivity extends ListActivity {
	private ProgressDialog dialog;
	protected List<Operation> operations;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		dialog = ProgressDialog.show(this, "", 
                "Fetching data. Please wait...", true);
		
		dialog.show();
		
		new Thread((new Runnable() {
		      @Override
		      public void run() {
		    	operations = request();
		  		
		    	if (operations != null) {
		    		onRequestFinishHandler.sendEmptyMessage(0);
		    	} else {
		    		
		    	}
		      }
		  })).start();
	}
	
	final Handler onRequestFinishHandler = new Handler() {
	    public void handleMessage(Message m) {
	        dialog.dismiss();
	        setListAdapter(new ViewDetailsAdapter(ViewDetailsActivity.this, R.layout.accountline, operations));
	    }
	};

	protected List<Operation> request() {
		RestClient client = new RestClient(
				"http://wadmanager.lo2k.net/remote/bank/1");

		try {
			client.Execute(RequestMethod.POST);
		} catch (Exception e) {
			e.printStackTrace();
			
			Context context = getApplicationContext();
			CharSequence text = "Can't have a connection.\n"+e.toString();
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

			// critical error 
			// finish activity
			finish();
			return null;
		}

		String response = client.getResponse();
		/*
		 * Reader r = new
		 * InputStreamReader(getJSONData("http://wadmanager.lo2k.net/remote/bank/1"
		 * ));
		 */

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

			@Override
			public Date deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context)
					throws JsonParseException {

				SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				String date = json.getAsJsonPrimitive().getAsString();
				try {
					return format.parse(date);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
		});

		Gson gson = builder.create();

		// gson.
		Type listType = new TypeToken<List<Operation>>() {
		}.getType();
		Log.v("DEBUG", "begin djson");
		try {
			List<Operation> operations = gson.fromJson(response, listType);
			Log.v("DEBUG", "" + operations.size());

			return operations;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
