package ke.afyamsaada.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ke.afyamsaada.R;
import ke.afyamsaada.model.Facility;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {

    public interface OnFacilityClickListener {
        void onFacilityClick(Facility facility);
        void onCallClick(Facility facility);
    }

    private List<Facility> facilityList = new ArrayList<>();
    private final OnFacilityClickListener listener;

    public FacilityAdapter(OnFacilityClickListener listener) {
        this.listener = listener;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilityList = facilities != null ? facilities : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility, parent, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        Facility facility = facilityList.get(position);
        holder.bind(facility, listener);
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }

    static class FacilityViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView verifiedBadge;
        private final TextView countyLocationText;
        private final TextView servicesText;
        private final TextView phoneText;
        private final Button callButton;

        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_facility_name);
            verifiedBadge = itemView.findViewById(R.id.text_verified_badge);
            countyLocationText = itemView.findViewById(R.id.text_county_location);
            servicesText = itemView.findViewById(R.id.text_services);
            phoneText = itemView.findViewById(R.id.text_phone);
            callButton = itemView.findViewById(R.id.button_call);
        }

        public void bind(final Facility facility, final OnFacilityClickListener listener) {
            nameText.setText(facility.getName());
            verifiedBadge.setVisibility(facility.isVerified() ? View.VISIBLE : View.GONE);
            countyLocationText.setText(facility.getCounty() + " • " + facility.getLocation());
            servicesText.setText(facility.getServices());
            phoneText.setText("📞 " + facility.getContactPhone());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFacilityClick(facility);
                }
            });

            callButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCallClick(facility);
                }
            });
        }
    }
}
