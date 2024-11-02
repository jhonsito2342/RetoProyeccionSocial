/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba;

import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Jhon
 */
public class Main {

    public static void main(String[] args) {
        Tienda tienda = new Tienda();
        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;
        while (continuar) {
            System.out.println("Ingrese el nombre de la persona:");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese la fecha de compra (YYYY-MM-DD):");
            String fecha = scanner.nextLine();

            System.out.println("Ingrese el código del artículo:");
            String codigoArticulo = scanner.nextLine();

            System.out.println("Ingrese la cantidad comprada:");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            tienda.registrarCompra(nombre, fecha, codigoArticulo, cantidad);

            System.out.println("¿Desea registrar otra compra? (si/no):");
            continuar = scanner.nextLine().equalsIgnoreCase("si");
        }

        // Generar reportes
        System.out.println("\n--- Reportes ---");
        System.out.println("Ingrese la fecha para el reporte diario (YYYY-MM-DD):");
        LocalDate fechaReporteDiario = LocalDate.parse(scanner.nextLine());
        tienda.generarReporteDiario(fechaReporteDiario);

        System.out.println("\nIngrese la fecha de inicio de la semana para el reporte semanal (YYYY-MM-DD):");
        LocalDate fechaReporteSemanal = LocalDate.parse(scanner.nextLine());
        tienda.generarReporteSemanal(fechaReporteSemanal);

        scanner.close();
    }
}
