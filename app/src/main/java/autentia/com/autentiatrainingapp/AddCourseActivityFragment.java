package autentia.com.autentiatrainingapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import autentia.com.autentiatrainingapp.command.Course;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddCourseActivityFragment extends Fragment {

    private Button submitButton = null;
    private CheckBox activeCheckBox = null;
    private Spinner teacherSpinner = null;
    private EditText titleEditText = null;
    private Spinner levelSpinner = null;
    private EditText hoursEditText = null;

    public AddCourseActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerUIComponents();
        addSubmitButtonListener();
    }

    private void registerUIComponents() {
        submitButton = (Button) getView().findViewById(R.id.add_course_submit_button);
        activeCheckBox = (CheckBox) getView().findViewById(R.id.add_course_checkBox_active);
        teacherSpinner = (Spinner) getView().findViewById(R.id.add_course_spinner_teacher);
        titleEditText = (EditText) getView().findViewById(R.id.add_course_editText_title);
        levelSpinner = (Spinner) getView().findViewById(R.id.add_course_spinner_level);
        hoursEditText = (EditText) getView().findViewById(R.id.add_course_editText_hours);
    }

    private void addSubmitButtonListener() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean active = activeCheckBox.isChecked();
                String teacher = String.valueOf(teacherSpinner.getSelectedItem());
                String title = titleEditText.getText().toString();
                String level = String.valueOf(levelSpinner.getSelectedItemId());
                String hours = hoursEditText.getText().toString();
                if (validate(title, hours)) {
                    Course course = new Course(active, teacher, title, level, hours);
                    Gson gson = new Gson();
                    String json = gson.toJson(course);
                    try {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.ServiceURL.COURSE_SAVE, new JSONObject(json), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display success message
                                Toast.makeText(getActivity(),
                                        getActivity().getString(R.string.add_course_msg_success),
                                        Toast.LENGTH_SHORT).show();
                                // Go to main activity
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                getActivity().startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Display error message
                                Toast.makeText(getActivity(),
                                        getActivity().getString(R.string.add_course_msg_error),
                                        Toast.LENGTH_SHORT).show();
                                // Log error
                                Log.e("Course Save", error.getMessage());
                            }
                        });

                        VolleyApplication.getInstance().getRequestQueue().add(request);
                    } catch (Exception e) {
                        // Display error message
                        Toast.makeText(getActivity(),
                                getActivity().getString(R.string.add_course_msg_error),
                                Toast.LENGTH_SHORT).show();
                        // Log error
                        Log.e("Course Save", e.getMessage());
                    }
                }
            }
        });
    }

    private boolean validate(String title, String hours) {
        boolean result = true;
        if (StringUtils.isBlank(title)) {
            Toast.makeText(getActivity(),
                    getActivity().getString(R.string.add_course_field_title) + " " + getActivity().getString(R.string.msg_validation_mandatory),
                    Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (StringUtils.isBlank(hours)) {
            Toast.makeText(getActivity(),
                    getActivity().getString(R.string.add_course_field_hours) + " " + getActivity().getString(R.string.msg_validation_mandatory),
                    Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }
}
