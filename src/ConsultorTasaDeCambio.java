import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class ConsultorTasaDeCambio {

    private static final Logger LOGGER = Logger.getLogger(ConsultorTasaDeCambio.class.getName());

    public static BigDecimal obtenerTasaDeCambio(String monedaDesde, String monedaHacia) {
        try {
            String apiKey = Config.getApiKey();
            String urlStr = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaDesde;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject json = new JSONObject(content.toString());
            return BigDecimal.valueOf(json.getJSONObject("conversion_rates").getDouble(monedaHacia));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la tasa de cambio", e);
            return null; // Error en la obtención de la tasa de cambio
        }
    }
}
