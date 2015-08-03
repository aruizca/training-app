package autentia.com.autentiatrainingapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

import autentia.com.autentiatrainingapp.command.Course;

public class CourseListAdapter extends ArrayAdapter<Course> {

    HashMap<Course, Integer> mIdMap = new HashMap<>();

    public CourseListAdapter(Context context, int textViewResourceId,
                              List<Course> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        Course item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
