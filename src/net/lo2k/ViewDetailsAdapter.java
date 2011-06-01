package net.lo2k;

import java.util.List;

import models.Operation;
import android.content.Context;
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
                TextView item = (TextView)view.findViewById(R.id.accountLineDesc); 
                item.setText(operation.comment);
                //tvIem.setText(twit.getTitle()); ==> ligne 33 (c'est la ou cela plante)
            }
            
         return view;

     }
}
