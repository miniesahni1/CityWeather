package cityweatherforcast.com.weather.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cityweatherforcast.com.weather.R;
import cityweatherforcast.com.weather.bean.Weather;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder> {
    Context context;
    List<Weather> weatherForecastList;

    public RecyclerAdapter(List<Weather> weatherForecastList) {
        this.weatherForecastList = weatherForecastList;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        Weather weather = weatherForecastList.get(position);
        holder.tDayTemp.setText(String.valueOf(weather.getDayTemp()) + " C");
        holder.tMinTemp.setText(String.valueOf(weather.getMinTemp()) + " C");
        holder.tMaxTemp.setText(String.valueOf(weather.getMaxTemp()) + " C");
        holder.tMain.setText(String.valueOf(weather.getMain()));
        holder.tDescription.setText((String.valueOf(weather.getDescription()) + ", wind speed: " + String.valueOf(weather.getWindSpeed()) + " km/h").toLowerCase());
        holder.tDescription.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return weatherForecastList.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        private TextView tDayTemp;
        private TextView tMinTemp;
        private TextView tMaxTemp;
        private TextView tMain;
        private TextView tDescription;


        public RecycleViewHolder(View itemView) {
            super(itemView);
            tDayTemp = (TextView) itemView.findViewById(R.id.dayTemp);
            tMinTemp = (TextView) itemView.findViewById(R.id.minTemp);
            tMaxTemp = (TextView) itemView.findViewById(R.id.maxTemp);
            tMain = (TextView) itemView.findViewById(R.id.weatherMain);
            tDescription = (TextView) itemView.findViewById(R.id.weatherDescription);
        }
    }
}
