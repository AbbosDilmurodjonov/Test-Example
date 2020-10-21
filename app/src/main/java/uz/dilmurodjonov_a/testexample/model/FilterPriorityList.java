package uz.dilmurodjonov_a.testexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class FilterPriorityList {

    @SerializedName("rows")
    private List<FilterPriority> filterList;

    public List<FilterPriority> getFilterPrioriyList() {
        return filterList;
    }
}
