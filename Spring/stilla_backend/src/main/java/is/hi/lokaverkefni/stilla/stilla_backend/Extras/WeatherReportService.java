package is.hi.lokaverkefni.stilla.stilla_backend.Extras;

import is.hi.lokaverkefni.stilla.stilla_backend.Extras.Forecasts;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherReportService {

    @GET("https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&view=xml&ids=422")
    Call<Forecasts> getWeatherReport();

}
