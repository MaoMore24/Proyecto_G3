
package proy_final_g3;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Proyecto_G3_final {

    public static void main(String[] args) {
        //Inicio main    

        // Variables
        String menuPrincipal, menuReportes, menuAccesos, menuQuickpass, dateTime, filial, codigo, placa, consultaFilial, filialElim, menuBusqueda;
        int accesosRegistrados = 0, accesosFilial = 0, quickRegistrados = 0;
        int quickActivos = 0, quickInactivos = 0, quickElim = 0;
        Archivos archivo = new Archivos();
        FileWriter escribir;
        PrintWriter linea;

        // Manejo archivo
        archivo.crearArchivo();

        // Creación del arreglo de objetos para almacenar datos activos y eliminados
        GestionQuickpass infoQuickpasses = new GestionQuickpass(10);
        GestionQuickpass quickEliminados = new GestionQuickpass(50);

        // Obtener fecha y hora
        dateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss").format(LocalDateTime.now());

        // Menú de ingreso al sistema
        do {
            menuPrincipal = JOptionPane.showInputDialog(null, "--- Bienvenid@ al sistema de gestión de accesos"
                    + " del Condominio Valhalla ---"
                    + "\n                                     --- Módulos de acceso ---"
                    + "\n\n1) Módulo Gestión Quickpass"
                    + "\n2) Módulo Gestión de accesos"
                    + "\n3) Módulo Reportes"
                    + "\n4) Salir"
                    + "\n\nSeleccione el módulo de su interés: ");

            switch (menuPrincipal) {
                case "1":
                    do {
                        menuQuickpass = JOptionPane.showInputDialog(null, "Seleccione la opción de su interés: "
                                + "\n\n1) Agregar Quickpass"
                                + "\n2) Consultar Quickpass"
                                + "\n3) Eliminar Quickpass"
                                + "\n4) Cambiar estado"
                                + "\n5) Regresar");
                        switch (menuQuickpass) {
                            case "1":
                                filial = JOptionPane.showInputDialog("Digite la filial: ");
                                codigo = JOptionPane.showInputDialog("Digite el codigo: ");
                                while (!codigo.startsWith("101") || codigo.length() != 10) {
                                    JOptionPane.showMessageDialog(null, "Código incorrecto. El código debe iniciar con 101"
                                            + " y tener una longitud de 10 caracteres");
                                    codigo = JOptionPane.showInputDialog("Digite el código: ");
                                }
                                placa = JOptionPane.showInputDialog("Digite la placa: ");
                                if (infoQuickpasses.llenarQuickpass(new Quickpass(filial, codigo, placa, Estado.Activo))) {
                                    JOptionPane.showMessageDialog(null, "Ingreso completado con éxito");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error al agregar. Espacio insuficiente");
                                }
                                break;
                            case "2":
                                JOptionPane.showMessageDialog(null, infoQuickpasses.toString());
                                break;
                            case "3":
                                menuBusqueda=JOptionPane.showInputDialog("Digite:\n1) Buscar y eliminar por código\n2) Buscar y eliminar por placa");
                                switch(menuBusqueda)
                                {
                                    case "1" -> {
                                        int codResultado=-1;
                                        codigo=JOptionPane.showInputDialog("Digite el código a buscar y eliminar:");
                                        codResultado=infoQuickpasses.eliminarQuickpassPorCodigo(codigo);
                                        if(codResultado==0)
                                            JOptionPane.showMessageDialog(null, "La eliminación por código se ejecutó exitosamente.");
                                        else
                                            JOptionPane.showMessageDialog(null, "ADVERTENCIA: La eliminación por código falló."+(codResultado==2?"El código no fue encontrado.":"La lista de quickpass está vacía."));
                                        
                                }
                                    case "2" -> {
                                        int codResultado=-1;
                                        placa=JOptionPane.showInputDialog("Digite la placa a buscar y eliminar:");
                                        codResultado=infoQuickpasses.eliminarQuickpassPorPlaca(placa);
                                        if(codResultado==0)
                                            JOptionPane.showMessageDialog(null, "La eliminación por placa se ejecutó exitosamente.");
                                        else
                                            JOptionPane.showMessageDialog(null, "ADVERTENCIA: La eliminación por placa falló."+(codResultado==2?"La placa no fue encontrada.":"La lista de quickpass está vacía."));
                                }
                                     default -> JOptionPane.showMessageDialog(null, "Seleccione una opción valida");
                                }
                                break;
                             
                            case "4":
                                filial = JOptionPane.showInputDialog("Ingrese la filial a la cual"
                                        + " le cambiará el estado: ");
                                String estadoCambio = JOptionPane.showInputDialog("Ingrese el estado (activo/inactivo): ");
                                if (estadoCambio.toLowerCase().equals("activo")) {
                                    JOptionPane.showMessageDialog(null, "El estado es activo.");
                                    Estado nuevoEstado = Estado.Activo;
                                    infoQuickpasses.cambiarEstado(filial, nuevoEstado);
                                } else if (estadoCambio.toLowerCase().equals("inactivo")) {
                                    JOptionPane.showMessageDialog(null, "El estado es inactivo.");
                                    Estado nuevoEstado = Estado.Inactivo;
                                    quickInactivos++;
                                    quickActivos--;
                                    infoQuickpasses.cambiarEstado(filial, nuevoEstado);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Ingrese un estado correcto.");
                                }
                                break;
                            case "5":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Seleccione una opción valida");
                                break;
                        }
                    } while (!menuQuickpass.equals("5"));
                    break;

                case "2":
                    do {
                        menuAccesos = JOptionPane.showInputDialog(null, "Seleccione la opción de su interés: "
                                + "\n\n1) Registrar la actividad"
                                + "\n2) Consultar histórico de acceso por filial"
                                + "\n3) Consultar por rango de fechas"
                                + "\n4) Consultar accesos por código"
                                + "\n5) Consultar accesos por placa"
                                + "\n6) Regresar");
                        switch (menuAccesos) {
                            case "1":
                                filial = JOptionPane.showInputDialog("Ingrese la filial: ");
                                String resultado = infoQuickpasses.consultarQuickpass(filial);
                                archivo.escribirArchivo("Condición: " + resultado + "; Fecha: " + dateTime);
                                JOptionPane.showMessageDialog(null, resultado);
                                break;
                            case "2":
                                consultaFilial = JOptionPane.showInputDialog("Ingrese la filial para consultar:");
                                String historico = archivo.leerArchivoFilial(consultaFilial);
                                JOptionPane.showMessageDialog(null, historico);
                                break;
                            case "3":
                                String fechaInicio = JOptionPane.showInputDialog("Ingrese la fecha de inicio (dd-MM-yyyy):");
                                String fechaFin = JOptionPane.showInputDialog("Ingrese la fecha de fin (dd-MM-yyyy):");
                                String historicoFechas = archivo.leerArchivoPorRangoFechas(fechaInicio, fechaFin);
                                JOptionPane.showMessageDialog(null, historicoFechas);
                                break;
                            case "4":
                                String codigoConsulta = JOptionPane.showInputDialog("Ingrese el código para consultar:");
                                String historicoCodigo = archivo.leerArchivoPorCodigo(codigoConsulta);
                                JOptionPane.showMessageDialog(null, historicoCodigo);
                                break;
                            case "5":
                                String placaConsulta = JOptionPane.showInputDialog("Ingrese la placa para consultar:");
                                String historicoPlaca = archivo.leerArchivoPorPlaca(placaConsulta);
                                JOptionPane.showMessageDialog(null, historicoPlaca);
                                break;
                            case "6":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Seleccione una opción valida");
                        }
                    } while (!menuAccesos.equals("6"));
                    break;

                case "3":
                    do {
                        menuReportes = JOptionPane.showInputDialog(null, "Seleccione la opción de su interés: "
                                + "\n\n1) Reporte de total de accesos registrados"
                                + "\n2) Reporte de total de accesos por filial"
                                + "\n3) Reporte de total de quickpass registrados"
                                + "\n4) Reporte de total de quickpass activos e inactivos"
                                + "\n5) Reporte de total de quickpass eliminados"
                                + "\n6) Regresar");
                        switch (menuReportes) {
                            case "1":
                                int totalAccesos = archivo.contarAccesosTotales();
                                JOptionPane.showMessageDialog(null, "Total de accesos registrados: "+totalAccesos);
                                break;
                            case "2":
                                String filialConsulta = JOptionPane.showInputDialog("Ingrese la filial para consultar:");
                                int accesosPorFilial = archivo.contarAccesosPorFilial(filialConsulta);
                                JOptionPane.showMessageDialog(null, "Total de accesos en la filial " + filialConsulta + ": " + accesosPorFilial);
                                break;
                            case "3":
                                JOptionPane.showMessageDialog(null, "Total de quickpass registrados: " + infoQuickpasses.TotalQuickpass());
                                break;
                            case "4":
                                JOptionPane.showMessageDialog(null, "Quickpass activos: " + quickActivos +
                                      "\nQuickpass inactivos: " + infoQuickpasses.consultarQuickInactivos());
                                break;
                            case "5":
                                JOptionPane.showMessageDialog(null, "Total de quickpass eliminados: " + infoQuickpasses.consultarQuickEliminados());
                                break;
                            case "6":
                                break;
                        }
                    } while (!menuReportes.equals("6"));
                    break;

                case "4":
                    JOptionPane.showMessageDialog(null, "Gracias por utilizar el sistema del Condominio. ¡Hasta luego!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Seleccione una opción valida");
            }
        } while (!menuPrincipal.equals("4"));
    }

    
}
