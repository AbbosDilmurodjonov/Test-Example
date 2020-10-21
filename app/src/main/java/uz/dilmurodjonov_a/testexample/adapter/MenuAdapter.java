package uz.dilmurodjonov_a.testexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.dilmurodjonov_a.testexample.R;
import uz.dilmurodjonov_a.testexample.model.Model;

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 21.10.2020.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.mViewHolder> {

    private List<Model> modelList;
    private Context context;

    public MenuAdapter(Context context) {

        this.context = context;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public void clearList() {
        if (modelList != null) {
            modelList.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);

        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        Model model = modelList.get(position);

        if (model == null) return;
        holder.idText.setText("ID: " + model.getId());
        holder.nameText.setText("Name: " + model.getName());

        String content = "";
        content += "Task Priorities Name: " + model.getTaskPrioritiesName() + "\n";
        content += "Task Types Name: " + model.getTaskTypesName() + "\n";
        content += "Task Statuses Name: " + model.getTaskStatusesName() + "\n";

        content += "Task priority Id: " + model.getTaskPrioritiesId() + "\n";
        content += "Task type Id: " + model.getTaskTypesId() + "\n";
        content += "Task Status Id: " + model.getTaskStatusesId() + "\n\n";

        content += "Desc: " + model.getDescription() + "\n";
        content += "Parent Id: " + model.getParentId() + "\n";
        content += "Task platforms Id: " + model.getTaskPlatformsId() + "\n";
        content += "Curr Executor Id: " + model.getCurrExecutorId() + "\n";
        content += "Expired date: " + model.getExpiredDate() + "\n";
        content += "Created date: " + model.getCreatedDate() + "\n";
        content += "Created Users Id: " + model.getCreatedUsersId() + "\n";
        content += "Is Editable: " + model.getIsEditable() + "\n";
        content += "Task Code: " + model.getTaskCode() + "\n";
        content += "Projects Id: " + model.getProjectsId() + "\n";
        content += "Projects Name: " + model.getProjectsName() + "\n";
        content += "Projects Short Name: " + model.getProjectsShortName() + "\n";
        content += "Projects Color: " + model.getProjectsColor() + "\n";
        content += "Parent Name: " + model.getParentName() + "\n";

        content += "Task Platforms Name: " + model.getTaskPlatformsName() + "\n";
        content += "Curr Executor Name: " + model.getCurrExecutorName() + "\n";
        content += "Created Users Name: " + model.getCreatedUsersName() + "\n";
        content += "Is Late: " + model.getIsLate() + "\n";
        content += "Left Days: " + model.getLeftDays() + "\n";
        content += "Undone Tasks: " + model.getUndoneTasks() + "\n";
        content += "Task Status Date: " + model.getTaskStatusDate() + "\n";
        content += "Is Done Task: " + model.getIsDoneTask() + "\n\n";


        holder.subText.setText(content);
    }

    @Override
    public int getItemCount() {
        if (modelList != null)
            return modelList.size();
        return 0;
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        private TextView idText;
        private TextView nameText;
        private ImageView expandImage;

        private LinearLayout subLayout;
        private TextView subText;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            idText = itemView.findViewById(R.id.item_id);
            nameText = itemView.findViewById(R.id.item_name);
            expandImage = itemView.findViewById(R.id.image_expand);
            subLayout = itemView.findViewById(R.id.sub_layout);
            subText = itemView.findViewById(R.id.item_sub);

            expandImage.setOnClickListener(v -> {
                if (subLayout.getVisibility() == View.VISIBLE) {
                    collapse(subLayout);
                    expandImage.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {
                    expand(subLayout);
                    expandImage.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            });
        }


        public void expand(final View v) {
            int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
            int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
            final int targetHeight = v.getMeasuredHeight();

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.getLayoutParams().height = 1;
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1
                            ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // Expansion speed of 1dp/ms
            a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }

        public void collapse(final View v) {
            final int initialHeight = v.getMeasuredHeight();

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            // Collapse speed of 1dp/ms
            a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        }
    }
}
