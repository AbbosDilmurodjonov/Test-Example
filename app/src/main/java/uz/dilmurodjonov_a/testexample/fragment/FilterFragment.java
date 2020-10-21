package uz.dilmurodjonov_a.testexample.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
import uz.dilmurodjonov_a.testexample.model.FilterPriority;
import uz.dilmurodjonov_a.testexample.model.FilterPriorityList;
import uz.dilmurodjonov_a.testexample.model.FilterStatus;
import uz.dilmurodjonov_a.testexample.model.FilterStatusList;
import uz.dilmurodjonov_a.testexample.model.FilterType;
import uz.dilmurodjonov_a.testexample.model.FilterTypeList;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class FilterFragment extends Fragment {

    public static final String ARGS_1 = "uz.dilmurodjonov_a.testexample.fragment.priority";
    public static final String ARGS_2 = "uz.dilmurodjonov_a.testexample.fragment.type";
    public static final String ARGS_3 = "uz.dilmurodjonov_a.testexample.fragment.status";
    public static final String ARGS_FILTERED = "uz.dilmurodjonov_a.testexample.fragment.filter";

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private FilterDone filterDone;

    private LinearLayout layoutFilter;

    private List<Integer> filterPriority;
    private List<Integer> filterType;
    private List<Integer> filterStatus;


    public static FilterFragment newInstance(FilterDone filterDone,
                                             List<Integer> filterPriority,
                                             List<Integer> filterType,
                                             List<Integer> filterStatus) {

        Bundle args = new Bundle();

        args.putIntegerArrayList(ARGS_1, (ArrayList<Integer>) filterPriority);
        args.putIntegerArrayList(ARGS_2, (ArrayList<Integer>) filterType);
        args.putIntegerArrayList(ARGS_3, (ArrayList<Integer>) filterStatus);
        args.putBoolean(ARGS_FILTERED, true);

        FilterFragment fragment = new FilterFragment();
        fragment.setFilterDoneListener(filterDone);

        fragment.setArguments(args);
        return fragment;
    }

    public static FilterFragment newInstance(FilterDone filterDone) {

        FilterFragment fragment = new FilterFragment();
        fragment.setFilterDoneListener(filterDone);

        Bundle args = new Bundle();
        args.putBoolean(ARGS_FILTERED, false);

        fragment.setArguments(args);

        return fragment;
    }

    public void setFilterDoneListener(FilterDone filterDone) {
        this.filterDone = filterDone;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://furorprogress.uz/test/fp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        if (getArguments().getBoolean(ARGS_FILTERED, false)) {
            filterPriority = getArguments().getIntegerArrayList(ARGS_1);
            filterType = getArguments().getIntegerArrayList(ARGS_2);
            filterStatus = getArguments().getIntegerArrayList(ARGS_3);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> getFragmentManager().popBackStack());

        layoutFilter = v.findViewById(R.id.layout);

        loadPriority();

        return v;
    }

    private void loadPriority() {

        Call<FilterPriorityList> call = jsonPlaceHolderApi.getPriority();

        call.enqueue(new Callback<FilterPriorityList>() {
            @Override
            public void onResponse(Call<FilterPriorityList> call, Response<FilterPriorityList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                FilterPriorityList modelList = response.body();

                LinearLayout linearLayout = createLinearLayout();
                linearLayout.addView(createTitle("Task Priorities"));

                for (FilterPriority priority : modelList.getFilterPrioriyList()) {
                    CheckBox cb = createCheckBox(priority.getNameUz() + "/" + priority.getNameRu());
                    cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (filterPriority == null) filterPriority = new ArrayList<>();
                        if (isChecked && !(filterPriority.indexOf(priority.getId()) >= 0)) {
                            filterPriority.add(priority.getId());
                        } else if (!isChecked) {
                            filterPriority.remove(filterPriority.indexOf(priority.getId()));
                        }

                    });

                    if (filterPriority != null && filterPriority.indexOf(priority.getId()) >= 0)
                        cb.setChecked(true);
                    linearLayout.addView(cb);
                }

                addViewInLayout(linearLayout);
                loadType();

            }

            @Override
            public void onFailure(Call<FilterPriorityList> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadType() {
        Call<FilterTypeList> call = jsonPlaceHolderApi.getType();

        call.enqueue(new Callback<FilterTypeList>() {
            @Override
            public void onResponse(Call<FilterTypeList> call, Response<FilterTypeList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                FilterTypeList modelList = response.body();

                LinearLayout linearLayout = createLinearLayout();
                linearLayout.addView(createTitle("Task Type"));

                for (FilterType type : modelList.getFilterTypeList()) {
                    CheckBox cb = createCheckBox(type.getNameUz() + "/" + type.getNameRu());

                    cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (filterType == null) filterType = new ArrayList<>();
                        if (isChecked && !(filterType.indexOf(type.getId()) >= 0)) {
                            filterType.add(type.getId());
                        } else if (!isChecked) {
                            filterType.remove(filterType.indexOf(type.getId()));
                        }
                    });

                    if (filterType != null && filterType.indexOf(type.getId()) >= 0)
                        cb.setChecked(true);

                    linearLayout.addView(cb);
                }

                addViewInLayout(linearLayout);
                loadStatus();
            }

            @Override
            public void onFailure(Call<FilterTypeList> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadStatus() {
        Call<FilterStatusList> call = jsonPlaceHolderApi.getStatus();

        call.enqueue(new Callback<FilterStatusList>() {
            @Override
            public void onResponse(Call<FilterStatusList> call, Response<FilterStatusList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                FilterStatusList modelList = response.body();

                LinearLayout linearLayout = createLinearLayout();
                linearLayout.addView(createTitle("Task Status"));

                for (FilterStatus status : modelList.getFilterStatusList()) {
                    CheckBox cb = createCheckBox(status.getNameUz() + "/" + status.getNameRu());
                    cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (filterStatus == null) filterStatus = new ArrayList<>();
                        if (isChecked && !(filterStatus.indexOf(status.getId()) >= 0)) {
                            filterStatus.add(status.getId());
                        } else if (!isChecked) {
                            filterStatus.remove(filterStatus.indexOf(status.getId()));
                        }


                    });
                    if (filterStatus != null && filterStatus.indexOf(status.getId()) >= 0)
                        cb.setChecked(true);
                    linearLayout.addView(cb);
                }

                addViewInLayout(linearLayout);
            }

            @Override
            public void onFailure(Call<FilterStatusList> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.done) {
            getFragmentManager().popBackStack();

            if (filterPriority != null && filterPriority.size() == 0)
                filterPriority = null;

            if (filterType != null && filterType.size() == 0)
                filterType = null;

            if (filterStatus != null && filterStatus.size() == 0)
                filterStatus = null;

            filterDone.onDoneClick(filterPriority, filterType, filterStatus);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addViewInLayout(LinearLayout linearLayout) {
        layoutFilter.addView(linearLayout);

        View view = new View(getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        ));
        view.setBackgroundColor(Color.GRAY);

        layoutFilter.addView(view);
    }

    private LinearLayout createLinearLayout() {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        return linearLayout;
    }

    private TextView createTitle(String text) {

        TextView valueTV = new TextView(getContext());
        valueTV.setText(text);
        valueTV.setTextSize(24);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        par.setMargins(16, (int) (24 * getContext().getResources().getDisplayMetrics().density), 0, 16);
        valueTV.setLayoutParams(par);

        return valueTV;
    }

    private CheckBox createCheckBox(String name) {
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText(name);
        checkBox.setTextSize(20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins((int) (32 * getContext().getResources().getDisplayMetrics().density), 8, 0, 8);
        checkBox.setLayoutParams(params);

        return checkBox;
    }


}
