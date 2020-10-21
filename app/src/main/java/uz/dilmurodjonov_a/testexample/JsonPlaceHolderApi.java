package uz.dilmurodjonov_a.testexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uz.dilmurodjonov_a.testexample.model.FilterPriorityList;
import uz.dilmurodjonov_a.testexample.model.FilterStatusList;
import uz.dilmurodjonov_a.testexample.model.FilterTypeList;
import uz.dilmurodjonov_a.testexample.model.ModelList;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public interface JsonPlaceHolderApi {

    @GET("test-tasks")
    Call<ModelList> getAllModels();

    @GET("test-tasks")
    Call<ModelList> getModelsWithPage(
            @Query("page") int page,
            @Query("perPage") int count);

    @GET("task-priorities")
    Call<FilterPriorityList> getPriority();

    @GET("task-types")
    Call<FilterTypeList> getType();

    @GET("task-statuses")
    Call<FilterStatusList> getStatus();

}
