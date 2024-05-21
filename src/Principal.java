import java.math.BigDecimal;
import java.util.Scanner;

public class Principal {

    // Método para convertir de una moneda a otra usando BigDecimal
    public static BigDecimal convertir(BigDecimal cantidad, BigDecimal tasaOrigen, BigDecimal tasaDestino) {
        return cantidad.multiply(tasaDestino).divide(tasaOrigen, BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Monedas disponibles y sus tasas de conversión respecto al USD
        String[] monedas = {"USD", "EUR", "GBP", "JPY", "ARS"};
        BigDecimal[] tasas = {
                BigDecimal.valueOf(1.0),
                BigDecimal.valueOf(0.85),
                BigDecimal.valueOf(0.75),
                BigDecimal.valueOf(110.0),
                BigDecimal.valueOf(1020.0)
        }; // Ejemplo de tasas de conversión

        System.out.println("Conversor de Moneda");
        System.out.println("Monedas disponibles: ");
        for (int i = 0; i < monedas.length; i++) {
            System.out.println((i + 1) + ". " + monedas[i]);
        }

        // Leer la moneda de origen
        System.out.print("Selecciona la moneda de origen (1-4): ");
        int indiceMonedaOrigen = scanner.nextInt() - 1;

        // Leer la moneda de destino
        System.out.print("Selecciona la moneda de destino (1-4): ");
        int indiceMonedaDestino = scanner.nextInt() - 1;

        // Leer el monto a convertir
        System.out.print("Introduce el monto a convertir: ");
        BigDecimal cantidad = scanner.nextBigDecimal();

        // Realizar la conversión
        BigDecimal tasaOrigen = tasas[indiceMonedaOrigen];
        BigDecimal tasaDestino = tasas[indiceMonedaDestino];
        BigDecimal cantidadConvertida = convertir(cantidad, tasaOrigen, tasaDestino);

        // Mostrar el resultado
        System.out.printf("El monto convertido es: %.2f %s%n", cantidadConvertida, monedas[indiceMonedaDestino]);

        scanner.close();
    }
}
