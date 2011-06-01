package net.lo2k;

import java.util.List;

import models.Operation;
import models.Tag;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ViewDetailsAdapter extends ArrayAdapter<Operation> {

	public ViewDetailsAdapter(Context context, int textViewResourceId, List<Operation> objects) {
        super(context, textViewResourceId, objects);        
    }
	
	 @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         View view = convertView;
         
            if (view == null)
            {            
                LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //le layout représentant la ligne dans le listView
                view = li.inflate(R.layout.accountline, null); 
            }         
            Operation operation = (Operation) getItem(position);
            if (operation != null)
            {
            	
            	String name = operation.name.trim();
            	
            	if (name.length()>20) {
            		name = name.substring(0, 20);
            	}
            	
                ((TextView)view.findViewById(R.id.accountlineName)).setText(name); 
                ((TextView)view.findViewById(R.id.accountlineComment)).setText(operation.comment);
                ((TextView)view.findViewById(R.id.accountlineAmount)).setText(""+operation.amount);
                ((TextView)view.findViewById(R.id.accountlineDate)).setText(operation.date.toLocaleString());
                
                String tags = "";
                if (operation.tags != null) {
	                for (Tag t : operation.tags) {
	                	tags+=t.name+", ";
	                 }
                }
                
                ((TextView)view.findViewById(R.id.accountlineTags)).setText(tags);
                
                
                if (operation.amount < 0) {
                	((TextView)view.findViewById(R.id.accountlineAmount)).setTextColor(Color.RED);
                } else {
                	((TextView)view.findViewById(R.id.accountlineAmount)).setTextColor(Color.GREEN);
                }
                
            }
            
         return view;

     }
}
