package autentia.com.autentiatrainingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import autentia.com.autentiatrainingapp.adapter.CourseListAdapter;
import autentia.com.autentiatrainingapp.command.Course;

public class MainActivity extends AppCompatActivity {

    private List<Course> courses = new ArrayList<>();
    private TextView headerTextView = null;
    private ListView listView = null;
    private Boolean ascSortOrder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retieveAndShowExternalData();

        registerUIComponents();

        registerListeners();
    }

    private void registerUIComponents() {
        listView = (ListView) findViewById(R.id.listView);
        headerTextView = (TextView)findViewById(R.id.textView);
    }

    private void retieveAndShowExternalData() {
        JsonArrayRequest jsonArrayRequest = loadAndSetupCoursesList();
        VolleyApplication.getInstance().getRequestQueue().add(jsonArrayRequest);
    }

    private void registerListeners() {
        headerTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Sort courses alphabetically
                Collections.sort(courses, new Comparator<Course>() {
                    @Override
                    public int compare(Course course1, Course course2) {
                        if (ascSortOrder) {
                            return course1.getTitle().compareTo(course2.getTitle());
                        } else {
                            return course2.getTitle().compareTo(course1.getTitle());
                        }
                    }
                });
                // Next time we order differently
                ascSortOrder = !ascSortOrder;
                CourseListAdapter adapter = new CourseListAdapter(MainActivity.this,
                        android.R.layout.simple_list_item_1, courses);
                listView.setAdapter(adapter);
            }
        });
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
        for(int i =0; i < jsonArray.length(); i++) {
            try {
                courses.add(parseSingleCourseJson(jsonArray, i));
            } catch (JSONException e) {
                Log.e("JSON parsing Exception", e.toString());
            }
        }

        CourseListAdapter adapter = new CourseListAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, courses);
        listView.setAdapter(adapter);
    }

    private Course parseSingleCourseJson(JSONArray jsonArray, int i) throws JSONException {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Long id = jsonObject.getLong("id");
        Boolean active = jsonObject.getBoolean("active");
        String teacher = jsonObject.getString("teacher");
        String title = jsonObject.getString("title");
        Integer hours = jsonObject.getInt("hours");
        Integer level = jsonObject.getInt("level");
        return new Course(id, active, teacher, title, hours, level);
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
        if (id == R.id.add_course_menu_item) {
            Intent intent = new Intent(this, AddCourseActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
