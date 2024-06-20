import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Principal {

    private static final String ARCHIVO_MONEDAS = "monedas.json";
    private static final Logger LOGGER = Logger.getLogger(Principal.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Moneda> monedas = cargarMonedas();

        System.out.println("Conversor de Moneda");

        while (true) {
            System.out.println("Opciones:");
            System.out.println("1. Mostrar todas las monedas disponibles");
            System.out.println("2. Agregar nueva moneda");
            System.out.println("3. Convertir monedas");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    mostrarMonedas(monedas);
                    break;
                case 2:
                    agregarMoneda(scanner, monedas);
                    break;
                case 3:
                    convertirMoneda(scanner, monedas);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    private static void mostrarMonedas(List<Moneda> monedas) {
        System.out.println("Monedas disponibles:");
        for (int i = 0; i < monedas.size(); i++) {
            System.out.println((i + 1) + ". " + monedas.get(i).codigo());
        }
    }

    private static void agregarMoneda(Scanner scanner, List<Moneda> monedas) {
        System.out.print("Introduce el código de la nueva moneda (ej. MXN): ");
        String codigoNuevaMoneda = scanner.next().toUpperCase();
        BigDecimal tasaCambio = ConsultorTasaDeCambio.obtenerTasaDeCambio("USD", codigoNuevaMoneda);
        if (tasaCambio != null) {
            monedas.add(new Moneda(codigoNuevaMoneda, tasaCambio));
            guardarMonedas(monedas);
            System.out.println("Moneda agregada exitosamente.");
        } else {
            System.out.println("Error al obtener la tasa de cambio para la nueva moneda.");
        }
    }

    private static void convertirMoneda(Scanner scanner, List<Moneda> monedas) {
        mostrarMonedas(monedas);

        int indiceMonedaOrigen = obtenerIndiceMoneda(scanner, monedas, "origen");
        int indiceMonedaDestino = obtenerIndiceMoneda(scanner, monedas, "destino");

        System.out.print("Introduce el monto a convertir: ");
        BigDecimal cantidad = scanner.nextBigDecimal();

        BigDecimal tasaOrigen = monedas.get(indiceMonedaOrigen).tasaCambio();
        BigDecimal tasaDestino = monedas.get(indiceMonedaDestino).tasaCambio();
        BigDecimal cantidadConvertida = convertir(cantidad, tasaOrigen, tasaDestino);

        System.out.printf("El monto convertido es: %.2f %s%n", cantidadConvertida, monedas.get(indiceMonedaDestino).codigo());
    }

    private static int obtenerIndiceMoneda(Scanner scanner, List<Moneda> monedas, String tipo) {
        int indice = -1;
        while (indice < 0 || indice >= monedas.size()) {
            System.out.print("Selecciona la moneda de " + tipo + " (1-" + monedas.size() + "): ");
            if (scanner.hasNextInt()) {
                indice = scanner.nextInt() - 1;
                if (indice < 0 || indice >= monedas.size()) {
                    System.out.println("Selección inválida, por favor intenta de nuevo.");
                }
            } else {
                System.out.println("Entrada no válida. Por favor, introduce un número.");
                scanner.next(); // Limpiar entrada no válidat
            }
            scanner.nextLine(); // Limpiar el buffer
        }
        return indice;
    }

    public static BigDecimal convertir(BigDecimal cantidad, BigDecimal tasaOrigen, BigDecimal tasaDestino) {
        return cantidad.multiply(tasaDestino).divide(tasaOrigen, 2, RoundingMode.HALF_UP);
    }

    public static List<Moneda> cargarMonedas() {
        List<Moneda> monedas = new ArrayList<>();
        File archivo = new File(ARCHIVO_MONEDAS);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder json = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    json.append(linea);
                }
                JSONArray array = new JSONArray(json.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Moneda moneda = new Moneda(obj.getString("codigo"), obj.getBigDecimal("tasaCambio"));
                    monedas.add(moneda);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al cargar las monedas", e);
            }
        } else {
            inicializarMonedas(monedas);
        }
        return monedas;
    }

    private static void inicializarMonedas(List<Moneda> monedas) {
        monedas.add(new Moneda("USD", BigDecimal.ONE));
        monedas.add(new Moneda("EUR", ConsultorTasaDeCambio.obtenerTasaDeCambio("USD", "EUR")));
        monedas.add(new Moneda("GBP", ConsultorTasaDeCambio.obtenerTasaDeCambio("USD", "GBP")));
        monedas.add(new Moneda("JPY", ConsultorTasaDeCambio.obtenerTasaDeCambio("USD", "JPY")));
        monedas.add(new Moneda("ARS", ConsultorTasaDeCambio.obtenerTasaDeCambio("USD", "ARS")));
    }

    public static void guardarMonedas(List<Moneda> monedas) {
        JSONArray array = new JSONArray();
        for (Moneda moneda : monedas) {
            JSONObject obj = new JSONObject();
            obj.put("codigo", moneda.codigo());
            obj.put("tasaCambio", moneda.tasaCambio());
            array.put(obj);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_MONEDAS))) {
            writer.write(array.toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar las monedas", e);
        }
    }
}