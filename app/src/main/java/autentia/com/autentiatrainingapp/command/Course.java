package autentia.com.autentiatrainingapp.command;

import org.json.JSONObject;

import java.io.Serializable;

public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Boolean active;
    private String teacher;
    private String title;
    private Integer hours;
    private Integer level;

    public Course() {
    }

    public Course(Long id, Boolean active, String teacher, String title, Integer hours, Integer level) {
        this.id = id;
        this.active = active;
        this.teacher = teacher;
        this.title = title;
        this.hours = hours;
        this.level = level;
    }

    public Course(Boolean active, String teacher, String title, String level, String hours) {
        this.active = active;
        this.teacher = teacher;
        this.title = title;
        this.level = Integer.parseInt(level) + 1;
        this.hours = Integer.parseInt(hours);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return title + " - " + hours + "h (" + resolveLevel(level) + ")";
    }

    private String resolveLevel(Integer level) {
        String resolvedLevel = "";
        switch (level) {
            case 1:
                resolvedLevel = "Básico";
                break;
            case 2:
                resolvedLevel = "Intermedio";
                break;
            case 3:
                resolvedLevel = "Avanzado";
                break;
        }
        return  resolvedLevel;
    }
}