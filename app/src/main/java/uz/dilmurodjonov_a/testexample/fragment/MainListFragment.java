package uz.dilmurodjonov_a.testexample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.dilmurodjonov_a.testexample.FilterDone;
import uz.dilmurodjonov_a.testexample.JsonPlaceHolderApi;
import uz.dilmurodjonov_a.testexample.R;
import uz.dilmurodjonov_a.testexample.adapter.MenuAdapter;
import uz.dilmurodjonov_a.testexample.model.Model;
import uz.dilmurodjonov_a.testexample.model.ModelList;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class MainListFragment extends Fragment implements FilterDone {

    private int pageCount;
    private MenuAdapter adapter;
    private TextView pageText;

    private Button prevButton;
    private Button nextButton;

    private ProgressBar progressBar;
    private List<Model> allModel;

    private List<Integer> filterPriority;
    private List<Integer> filterStatus;
    private List<Integer> filterType;

    private boolean isFiltered = false;

    public static MainListFragment newInstance() {
        return new MainListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main_list, container, false);

        pageCount = 1;

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (isFiltered) {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);
            toolbar.setTitle("Result");
            toolbar.setNavigationOnClickListener(view -> {
                isFiltered = false;
                pageCount = 1;
                pageText.setText("Page: " + pageCount);
                prevButton.setEnabled(false);
                nextButton.setEnabled(true);
                load(pageCount);
                toolbar.setNavigationIcon(null);
                toolbar.setTitle("Test Example");
            });
        }

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MenuAdapter(getContext());
        recyclerView.setAdapter(adapter);


        pageText = v.findViewById(R.id.text_page_num);
        prevButton = v.findViewById(R.id.button_prev);
        nextButton = v.findViewById(R.id.button_next);
        progressBar = v.findViewById(R.id.progress);


        prevButton.setOnClickListener((view) -> onPrevButtonClick());
        nextButton.setOnClickListener((view) -> onNextButtonClick());

        if (!isFiltered)
            load(pageCount);
        else {
            loadFilter(pageCount);
            progressBar.setVisibility(View.INVISIBLE);
        }

        return v;
    }

    public void onNextButtonClick() {
        pageCount++;

        if (pageCount > 1) {
            prevButton.setEnabled(true);
        }

        if (!isFiltered) {
            load(pageCount);
        } else {
            loadFilter(pageCount);
        }
        pageText.setText("Page: " + pageCount);
    }

    public void onPrevButtonClick() {
        pageCount--;
        if (pageCount == 1) {
            prevButton.setEnabled(false);
        }
        nextButton.setEnabled(true);
        if (!isFiltered) {
            load(pageCount);
        } else {
            loadFilter(pageCount);
        }
        pageText.setText("Page: " + pageCount);
    }

    private void load(int page) {
        adapter.clearList();
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://furorprogress.uz/test/fp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ModelList> call = jsonPlaceHolderApi.getModelsWithPage(page, 10);

        call.enqueue(new Callback<ModelList>() {
            @Override
            public void onResponse(Call<ModelList> call, Response<ModelList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ModelList modelList = response.body();

                if (modelList.getModelList().size() < 10) nextButton.setEnabled(false);
                adapter.setModelList(modelList.getModelList());
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ModelList> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFilter(int pageCount) {
        adapter.clearList();

        List<Model> modelList = new ArrayList<>();

        for (int i = 10 * (pageCount - 1); i < 10 * pageCount && i < allModel.size(); i++) {
            modelList.add(allModel.get(i));
        }

        adapter.setModelList(modelList);

        if (modelList.size() < 10) nextButton.setEnabled(false);
        else nextButton.setEnabled(true);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            if (isFiltered) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FilterFragment.newInstance(this, filterPriority, filterType, filterStatus))
                        .addToBackStack(null)
                        .commit();
            } else {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FilterFragment.newInstance(this))
                        .addToBackStack(null)
                        .commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onDoneClick(List<Integer> priority, List<Integer> type, List<Integer> status) {

        filterPriority = priority;
        filterType = type;
        filterStatus = status;

        isFiltered = true;
        allModel = new ArrayList<>();
        pageCount = 1;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://furorprogress.uz/test/fp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ModelList> call = jsonPlaceHolderApi.getAllModels();

        call.enqueue(new Callback<ModelList>() {
            @Override
            public void onResponse(Call<ModelList> call, Response<ModelList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ModelList modelList = response.body();

                for (Model model : modelList.getModelList()) {

                    if ((filterPriority == null) || filterPriority != null && checkPriority(model.getTaskPrioritiesId()))
                        if ((filterType == null) || filterType != null && checkType(model.getTaskTypesId()))
                            if ((filterStatus == null) || filterStatus != null && checkStatus(model.getTaskStatusesId()))
                                allModel.add(model);

                }

                loadFilter(pageCount);
                Toast.makeText(getContext(), "List Size: " + allModel.size(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ModelList> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkStatus(int taskStatusesId) {

        for (int i = 0; i < filterStatus.size(); i++) {
            if (filterStatus.get(i) == taskStatusesId)
                return true;
        }
        return false;
    }

    private boolean checkType(int taskTypesId) {

        for (int i = 0; i < filterType.size(); i++) {
            if (filterType.get(i) == taskTypesId)
                return true;
        }
        return false;
    }

    private boolean checkPriority(int taskPrioritiesId) {

        for (int i = 0; i < filterPriority.size(); i++) {
            if (filterPriority.get(i) == taskPrioritiesId)
                return true;
        }
        return false;
    }
}
