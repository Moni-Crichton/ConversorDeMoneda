import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ConsultorTasaDeCambio {

    // Método para obtener las tasas de cambio desde una API externa
    public static double obtenerTasaDeCambio(String monedaDesde, String monedaHacia) {
        try {
            String claveAPI = "TU_API_KEY_AQUI"; // Coloca aquí tu clave de API
            String urlStr = "https://v6.exchangerate-api.com/v6/" + claveAPI + "/latest/" + monedaDesde;
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
            return json.getJSONObject("conversion_rates").getDouble(monedaHacia);

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Error en la obtención de la tasa de cambio
        }
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        double tasa = obtenerTasaDeCambio("USD", "EUR");
        if (tasa != -1) {
            System.out.println("Tasa de cambio USD a EUR: " + tasa);
        } else {
            System.out.println("Error al obtener la tasa de cambio.");
        }
    }
}
