package net.lo2k;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Operation;
import net.lo2k.RestClient.RequestMethod;
import android.app.ListActivity;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class ViewDetailsActivity extends ListActivity {
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setProgressBarVisibility(true);
	        List<Operation> operations = request();
	        
	        setListAdapter(new ViewDetailsAdapter(this, R.layout.accountline, operations));
	        
	        setProgressBarVisibility(false);
	    }
	  
	  protected List<Operation> request() {
			RestClient client = new RestClient("http://wadmanager.lo2k.net/remote/bank/1");
			//RestClient client = new RestClient("http://www.lo2k.net");
			//client.AddParam("accountType", "GOOGLE");
			//client.AddParam("source", "tboda-widgalytics-0.1");
			//client.AddParam("Email", _username);
			//client.AddParam("Passwd", _password);
			//client.AddParam("service", "analytics");
			//client.AddHeader("GData-Version", "2");

			try {
			    client.Execute(RequestMethod.POST);
			} catch (Exception e) {
			    e.printStackTrace();
			}

			String response = client.getResponse();
			/*Reader r = new InputStreamReader(getJSONData("http://wadmanager.lo2k.net/remote/bank/1"));*/
			Log.v("debug","oui ok");
			Log.v("DEBUG", response);
			
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

	            @Override
	            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
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
			
			//gson.
			Type listType = new TypeToken<List<Operation>>() {}.getType();
			Log.v("DEBUG", "begin djson");
			try {
				List<Operation> operations = gson.fromJson(response, listType);
				Log.v("DEBUG",""+operations.size());
				
				return operations;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	  }

}
