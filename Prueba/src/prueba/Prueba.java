/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Persona {

    String nombre;
    int dineroGastado = 0;
    ArrayList<Articulo> compras = new ArrayList<>();

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public int getDineroGastado() {
        return dineroGastado;
    }

    public void agregarCompra(Articulo articulo, int cantidad) {
        dineroGastado += articulo.valor * cantidad;
        for (int i = 0; i < cantidad; i++) {
            compras.add(articulo);
        }
    }
}

class Articulo {

    String codigo, nombre, tipo;
    int valor;

    public Articulo(String codigo, String nombre, String tipo, int valor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }
}

class Tienda {

    private Map<LocalDate, List<Persona>> comprasPorDia = new HashMap<>();
    private Map<String, Articulo> articulosDisponibles = new HashMap<>();

    public Tienda() {
        
        articulosDisponibles.put("JO-001", new Articulo("JO-001", "Chocolate Amargo", "dulce", 500));
        articulosDisponibles.put("JO-002", new Articulo("JO-002", "Gomitas", "dulce", 300));
        articulosDisponibles.put("JO-003", new Articulo("JO-003", "Caramelo", "dulce", 200));
        articulosDisponibles.put("JO-004", new Articulo("JO-004", "Chicle Menta", "dulce", 100));
        articulosDisponibles.put("JO-005", new Articulo("JO-005", "Agua Mineral", "bebida", 1600));
        articulosDisponibles.put("JO-006", new Articulo("JO-006", "Papas Fritas", "snack", 1200));
        articulosDisponibles.put("JO-007", new Articulo("JO-007", "Gaseosa", "bebida", 2500));
        articulosDisponibles.put("JO-008", new Articulo("JO-008", "Mani Salado", "snack", 500));

        
    }

    public void registrarCompra(String nombrePersona, String fecha, String codigoArticulo, int cantidad) {
        LocalDate fechaCompra = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE);

        // Buscar o crear la persona
        Persona persona = comprasPorDia.getOrDefault(fechaCompra, new ArrayList<>()).stream()
                .filter(p -> p.nombre.equals(nombrePersona)).findFirst()
                .orElseGet(() -> {
                    Persona nuevaPersona = new Persona(nombrePersona);
                    comprasPorDia.putIfAbsent(fechaCompra, new ArrayList<>());
                    comprasPorDia.get(fechaCompra).add(nuevaPersona);
                    return nuevaPersona;
                });

        // Buscar el artículo en los disponibles
        Articulo articulo = articulosDisponibles.get(codigoArticulo);
        if (articulo != null) {
            persona.agregarCompra(articulo, cantidad);
        } else {
            System.out.println("Artículo con código " + codigoArticulo + " no encontrado.");
        }
    }

    public void generarReporteDiario(LocalDate fecha) {
        List<Persona> comprasDelDia = comprasPorDia.getOrDefault(fecha, Collections.emptyList());
        System.out.println("\nReporte Diario para " + fecha + ":");

        Map<String, Integer> cantidadPorArticulo = new HashMap<>();
        Persona compradorTop = null;

        for (Persona persona : comprasDelDia) {
            if (compradorTop == null || persona.getDineroGastado() > compradorTop.getDineroGastado()) {
                compradorTop = persona;
            }
            for (Articulo articulo : persona.compras) {
                cantidadPorArticulo.put(articulo.nombre, cantidadPorArticulo.getOrDefault(articulo.nombre, 0) + 1);
            }
        }

        System.out.println("Comprador Principal del Día: " + (compradorTop != null ? compradorTop.nombre : "N/A"));
        if (!cantidadPorArticulo.isEmpty()) {
            String articuloTop = Collections.max(cantidadPorArticulo.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("Artículo Más Comprado del Día: " + articuloTop);
        }
    }

    public void generarReporteSemanal(LocalDate fechaInicio) {
        System.out.println("\nReporte Semanal (desde " + fechaInicio + "):");

        Map<String, Integer> gastosPorPersona = new HashMap<>();
        Map<String, Integer> cantidadPorArticulo = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            LocalDate fecha = fechaInicio.plusDays(i);
            List<Persona> comprasDelDia = comprasPorDia.getOrDefault(fecha, Collections.emptyList());

            for (Persona persona : comprasDelDia) {
                gastosPorPersona.put(persona.nombre, gastosPorPersona.getOrDefault(persona.nombre, 0) + persona.getDineroGastado());
                for (Articulo articulo : persona.compras) {
                    cantidadPorArticulo.put(articulo.nombre, cantidadPorArticulo.getOrDefault(articulo.nombre, 0) + 1);
                }
            }
        }

        System.out.println("Top Compradores de la Semana:");
        gastosPorPersona.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(entry -> System.out.println("Comprador: " + entry.getKey() + " - Total Gastado: $" + entry.getValue()));

        System.out.println("\nArtículos Más Comprados de la Semana:");
        cantidadPorArticulo.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(entry -> System.out.println("Artículo: " + entry.getKey() + " - Cantidad: " + entry.getValue()));
    }
}
