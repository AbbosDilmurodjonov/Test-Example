package uz.dilmurodjonov_a.testexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class FilterType {

    private int id;

    @SerializedName("name_ru")
    private String nameRu;

    @SerializedName("name_uk")
    private String nameUk;

    @SerializedName("name_uz")
    private String nameUz;

    private String description;

    private Integer ordering;

    public int getId() {
        return id;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getNameUk() {
        return nameUk;
    }

    public String getNameUz() {
        return nameUz;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOrdering() {
        return ordering;
    }
}
