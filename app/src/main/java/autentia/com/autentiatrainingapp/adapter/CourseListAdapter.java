package autentia.com.autentiatrainingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import autentia.com.autentiatrainingapp.R;

public class CourseListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values1;

    public CourseListAdapter(Context context, String[] objects) {
        super(context, R.layout.fragment_main, objects);
        this.context = context;
        this.values1 = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listView = inflater.inflate(R.layout.fragment_main, null);

//        TextView textView1 = (TextView)listView.findViewById(R.id.listView);
//        textView1.setText(values1[position]);

        return listView;
    }
}
