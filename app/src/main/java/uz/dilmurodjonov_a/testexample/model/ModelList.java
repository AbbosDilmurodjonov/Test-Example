package uz.dilmurodjonov_a.testexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class ModelList {

    @SerializedName("rows")
    private List<Model> modelList;

    public List<Model> getModelList() {
        return modelList;
    }

}
