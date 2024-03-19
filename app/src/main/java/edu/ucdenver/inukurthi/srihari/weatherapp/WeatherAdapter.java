package edu.ucdenver.inukurthi.srihari.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {


    Context context;
    ArrayList<WeatherModel> weatherModels;

    public WeatherAdapter(Context context, ArrayList<WeatherModel> weatherModels) {
        this.context = context;
        this.weatherModels = weatherModels;
    }


    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {

        WeatherModel weatherModel = weatherModels.get(position);
        holder.temperature.setText(weatherModel.getTemperature().concat("°C"));
        Picasso.get().load("http:".concat(weatherModel.getIcon())).into(holder.condition);
        holder.windSpeed.setText(weatherModel.getWindSpeed().concat("KPH"));
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
        try {
            Date date = inputFormat.parse(weatherModel.getTime());
            assert date != null;
            holder.time.setText(outputFormat.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int getItemCount() {
        return weatherModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView windSpeed, temperature, time;
        ImageView condition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            windSpeed = itemView.findViewById(R.id.windspeedInWeather);
            temperature = itemView.findViewById(R.id.temperatureInWeather);
            time = itemView.findViewById(R.id.time);
            condition = itemView.findViewById(R.id.conditionInWeather);


        }
    }
}
