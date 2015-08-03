package autentia.com.autentiatrainingapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import autentia.com.autentiatrainingapp.adapter.CourseListAdapter;
import autentia.com.autentiatrainingapp.command.Course;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonArrayRequest jsonArrayRequest = loadAndSetupCoursesList();
        VolleyApplication.getInstance().getRequestQueue().add(jsonArrayRequest);
    }

    @NonNull
    private JsonArrayRequest loadAndSetupCoursesList() {
        return new JsonArrayRequest("http://autentia-training-service.herokuapp.com/training-service/api/course/list",
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            parseCoursesListJson(jsonArray);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
    }

    private void parseCoursesListJson(JSONArray jsonArray) {
        List<Course> courses = new ArrayList<>();
        for(int i =0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Long id = jsonObject.getLong("id");
                Boolean active = jsonObject.getBoolean("active");
                String teacher = jsonObject.getString("teacher");
                String title = jsonObject.getString("title");
                Integer hours = jsonObject.getInt("hours");
                Integer level = jsonObject.getInt("level");
                courses.add(new Course(id, active, teacher, title, hours, level));
            } catch (JSONException e) {
                Log.e("JSON parsing Exception", e.toString());
            }
        }
        final ListView listView = (ListView) findViewById(R.id.listView);
        CourseListAdapter adapter = new CourseListAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, courses);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
