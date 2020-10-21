package uz.dilmurodjonov_a.testexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class FilterStatusList {

    @SerializedName("rows")
    private List<FilterStatus> filterList;

    public List<FilterStatus> getFilterStatusList() {
        return filterList;
    }
}
