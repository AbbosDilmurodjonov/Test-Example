package uz.dilmurodjonov_a.testexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class FilterTypeList {

    @SerializedName("rows")
    private List<FilterType> filterList;

    public List<FilterType> getFilterTypeList() {
        return filterList;
    }
}
