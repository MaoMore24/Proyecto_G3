package proy_final_g3;


import java.io.*;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Archivos {

    File miArchivo;

    public void crearArchivo() {
        miArchivo = new File("Historial.txt");
        try {
            if (miArchivo.createNewFile()) {
                JOptionPane.showMessageDialog(null, "El archivo fue creado");
            } else if (miArchivo != null) {
                // El archivo ya existe
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear el archivo");
            }
        } catch (IOException ex) {
            System.out.println("Error al crear el archivo.");
        }
    }

    public void escribirArchivo(String contenido) {
        try {
            FileWriter registro = new FileWriter("Historial.txt", true);
            registro.write(contenido + "\n");
            registro.close();
        } catch (IOException excepcion) {
            excepcion.printStackTrace(System.out);
        }
    }

    public String consultarArchivo(String filial, String codigo, String placa) {
        StringBuilder resultado = new StringBuilder();
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(filial+","+codigo+","+placa)) {
                    resultado.append(linea).append("\n");
                    encontrado= true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!encontrado){
            resultado.append("No se encontraron registros de acceso para la filial: " + filial);
        }
        return resultado.toString();
    }
    
    
    
    
    public String leerArchivoFilial(String filial) {
        StringBuilder resultado = new StringBuilder();
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Filial: " + filial)) {
                    resultado.append(linea).append("\n");
                    encontrado= true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!encontrado){
            resultado.append("No se encontraron registros de acceso para la filial: " + filial);
        }
        return resultado.toString();
    }

    public String leerArchivoPorRangoFechas(String fechaInicio, String fechaFin) {
        StringBuilder resultado = new StringBuilder();
        java.time.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Fecha: ")) {
                    String[] partes = linea.split("Fecha: ");
                    String fechaStr = partes[1];
                    java.time.LocalDateTime fecha = java.time.LocalDateTime.parse(fechaStr, formatter);
                    java.time.LocalDateTime inicio = java.time.LocalDateTime.parse(fechaInicio + ", 00:00:00", formatter);
                    java.time.LocalDateTime fin = java.time.LocalDateTime.parse(fechaFin + ", 23:59:59", formatter);
                    if ((fecha.isEqual(inicio) || fecha.isAfter(inicio)) && (fecha.isEqual(fin) || fecha.isBefore(fin))) {
                        resultado.append(linea).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado.toString();
    }

    
    
    
    public String leerArchivoPorCodigo(String codigo) {
        StringBuilder resultado = new StringBuilder();
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Código: " + codigo)) {
                    resultado.append(linea).append("\n");
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!encontrado){
            resultado.append("No se encontraron registros de acceso datos para el codigo: " + codigo);
        }
        return resultado.toString();
    }

    public String leerArchivoPorPlaca(String placa) {
        StringBuilder resultado = new StringBuilder();
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Placa: " + placa)) {
                    resultado.append(linea).append("\n");
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!encontrado){
            resultado.append("No se encontraron registros de accesos para la placa: " + placa);
        }
        return resultado.toString();
    }
    
    
    public int contarAccesosTotales() {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            while (br.readLine() != null) {
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contador;
        }
    
    public int contarAccesosPorFilial(String filial) {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Filial: " + filial)) {
                    contador++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contador;
        }   
    
    public void contarEstadosQuickpass() {
        int activos = 0;
        int inactivos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if(linea.contains("Estado: Activo")){
                    activos++;
                }else if(linea.contains("Estado: Inactivo")){
                    inactivos++;
                }}
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Registro de Quickpass por estados:\n\n"
                + "Quickpass activos: " + activos
                + "\nQuickpass inactivos: " + inactivos);
        
        }
    
    
    public int contarQuickpassEliminados() {
        int contador = 0; 
        try (BufferedReader br = new BufferedReader(new FileReader("Historial.txt"))) {
            String linea; 
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Eliminado")) {
                    contador++; 
                } } } 
        catch (IOException e) {
            e.printStackTrace(); 
        } 
        return contador; 
    }
    
    
    
    
    }
    
    
    
    

