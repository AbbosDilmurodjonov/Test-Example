package uz.dilmurodjonov_a.testexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class Model {

    private int id;

    private String name;


    private String description;

    @SerializedName("parent_id")
    private Integer parentId;


    @SerializedName("task_statuses_id")
    private int taskStatusesId;

    @SerializedName("task_types_id")
    private int taskTypesId;

    @SerializedName("task_priorities_id")
    private int taskPrioritiesId;

    @SerializedName("task_platforms_id")
    private int taskPlatformsId;

    @SerializedName("curr_executor_id")
    private int currExecutorId;

    @SerializedName("expired_date")
    private long expiredDate;

    @SerializedName("created_date")
    private long createdDate;

    @SerializedName("created_users_id")
    private int createdUsersId;

    @SerializedName("is_editable")
    private short isEditable;

    @SerializedName("task_code")
    private String taskCode;

    @SerializedName("projects_id")
    private Integer projectsId;

    @SerializedName("projects_name")
    private String projectsName;

    @SerializedName("projects_short_name")
    private String projectsShortName;

    @SerializedName("projects_color")
    private String projectsColor;

    @SerializedName("parent_name")
    private String parentName;

    @SerializedName("task_statuses_name")
    private String taskStatusesName;

    @SerializedName("task_types_name")
    private String taskTypesName;

    @SerializedName("task_priorities_name")
    private String taskPrioritiesName;

    @SerializedName("task_platforms_name")
    private String taskPlatformsName;

    @SerializedName("curr_executor_name")
    private String currExecutorName;

    @SerializedName("created_users_name")
    private String createdUsersName;

    @SerializedName("is_late")
    private short isLate;

    @SerializedName("left_days")
    private Integer leftDays;

    @SerializedName("undone_tasks")
    private Integer undoneTasks;

    @SerializedName("task_status_date")
    private long taskStatusDate;

    @SerializedName("is_done_task")
    private short isDoneTask;

    @SerializedName("project_categories_name")
    private String projectCategoriesName;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getParentId() {
        return parentId;
    }

    public int getTaskStatusesId() {
        return taskStatusesId;
    }

    public int getTaskTypesId() {
        return taskTypesId;
    }

    public int getTaskPrioritiesId() {
        return taskPrioritiesId;
    }

    public int getTaskPlatformsId() {
        return taskPlatformsId;
    }

    public int getCurrExecutorId() {
        return currExecutorId;
    }

    public long getExpiredDate() {
        return expiredDate;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public int getCreatedUsersId() {
        return createdUsersId;
    }

    public short getIsEditable() {
        return isEditable;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public Integer getProjectsId() {
        return projectsId;
    }

    public String getProjectsName() {
        return projectsName;
    }

    public String getProjectsShortName() {
        return projectsShortName;
    }

    public String getProjectsColor() {
        return projectsColor;
    }

    public String getParentName() {
        return parentName;
    }

    public String getTaskStatusesName() {
        return taskStatusesName;
    }

    public String getTaskTypesName() {
        return taskTypesName;
    }

    public String getTaskPrioritiesName() {
        return taskPrioritiesName;
    }

    public String getTaskPlatformsName() {
        return taskPlatformsName;
    }

    public String getCurrExecutorName() {
        return currExecutorName;
    }

    public String getCreatedUsersName() {
        return createdUsersName;
    }

    public short getIsLate() {
        return isLate;
    }

    public Integer getLeftDays() {
        return leftDays;
    }

    public Integer getUndoneTasks() {
        return undoneTasks;
    }

    public long getTaskStatusDate() {
        return taskStatusDate;
    }

    public short getIsDoneTask() {
        return isDoneTask;
    }

    public String getProjectCategoriesName() {
        return projectCategoriesName;
    }
}
