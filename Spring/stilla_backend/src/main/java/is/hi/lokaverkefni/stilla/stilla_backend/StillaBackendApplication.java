package is.hi.lokaverkefni.stilla.stilla_backend;

import is.hi.lokaverkefni.stilla.stilla_backend.Extras.Forecasts;
import is.hi.lokaverkefni.stilla.stilla_backend.Extras.WeatherReportService;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.io.IOException;

@SpringBootApplication
@RestController
public class StillaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StillaBackendApplication.class, args);
	}



























	/**
	 * TODO: REOVE ÞETTA ER ÓÞARFA KLASI SEM MUN EKKI VERA NOTAÐUR ÞVI ÞETTA ER KOMIÐ Á CLIENT SIDE
	 * @param stationId
	 * @return
	 */
	@GetMapping("/xml")
	public Response<Forecasts> getJsonFromVedur(@RequestParam(value = "stationId") int stationId) {
		/*String url = "https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&view=xml&ids=" + stationId;
		String json = XmlRequest.xmlRequestTo(url);
		System.out.print(json);
		return json;
		 */

		String API_BASE_URL = "http://localhost:8080";

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.client(new OkHttpClient())
				.addConverterFactory(SimpleXmlConverterFactory.create())
				.build();

		WeatherReportService weatherReportService = retrofit.create(WeatherReportService.class);
		Response<Forecasts> response = null;
		try {
			response = weatherReportService.getWeatherReport().execute();
			System.out.print("no error this worked " + response.body().getStation().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}
}


