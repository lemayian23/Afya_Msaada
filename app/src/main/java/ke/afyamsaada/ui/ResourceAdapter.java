package ke.afyamsaada.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ke.afyamsaada.R;
import ke.afyamsaada.model.Resource;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {

    public interface OnResourceClickListener {
        void onResourceClick(Resource resource);
    }

    private List<Resource> resourceList = new ArrayList<>();
    private final OnResourceClickListener listener;

    public ResourceAdapter(OnResourceClickListener listener) {
        this.listener = listener;
    }

    public void setResources(List<Resource> resources) {
        this.resourceList = resources != null ? resources : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        holder.bind(resource, listener);
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    static class ResourceViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        private final TextView titleText;
        private final TextView summaryText;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.text_category);
            titleText = itemView.findViewById(R.id.text_title);
            summaryText = itemView.findViewById(R.id.text_summary);
        }

        public void bind(final Resource resource, final OnResourceClickListener listener) {
            categoryText.setText(resource.getCategory());
            titleText.setText(resource.getTitle());
            summaryText.setText(resource.getSummary());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onResourceClick(resource);
                }
            });
        }
    }
}
