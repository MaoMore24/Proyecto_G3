
package proy_final_g3;

import java.io.*;
import java.util.Arrays;
import javax.swing.JOptionPane;



public class GestionQuickpass {
    
    private Quickpass[] listaQuickpass;
    private Quickpass[] quickEliminado;
    private int cant, cantElim;
    private int totalActivos=0, totalInactivos=0 ;
    FileWriter escribir;
    PrintWriter linea;
    
    
    //Constructor
    public GestionQuickpass(int tamanno){
        this.listaQuickpass = new Quickpass[tamanno];
        cant=0;
        this.quickEliminado = new Quickpass[tamanno];
        cantElim=0;
    }
    
    
    
    //Llenar la lista de quickpass
    public boolean llenarQuickpass(Quickpass pQuickpass){
        
        if (cant<this.listaQuickpass.length){
            for (int i=0; i<this.listaQuickpass.length; i++){
                if(this.listaQuickpass[i]==null){
                    this.listaQuickpass[i] = pQuickpass;//asignación
                    cant= cant+1;
                    totalActivos++;
                    try{
                    escribir = new FileWriter("Historial.txt", true);                
                    linea = new PrintWriter(escribir);
                    linea.println(listaQuickpass[i]);
                    linea.close();
                    }catch(IOException ex){
                    System.out.println("Error al registrar");
                }
                    break;
                }
            }
            return true;
        }else {
            return false;
        }        
    }
    
    //Inserta el quickpass en la lista de eliminados
    public boolean insertarQuickpassEliminado(Quickpass eQuickpass){
            for (int i=0; i<this.quickEliminado.length-1; i++){
                if(this.quickEliminado[i]==null){
                    this.quickEliminado[i] = eQuickpass;//asignación
                    cantElim++;
                    return true;
                }
            }
            return false;        
    } 
    
    //Cambiar el estado
    public boolean cambiarEstado(String filial, Estado nuevoEstado){
        if(cant>0){
            for(int i=0;i<this.listaQuickpass.length;i++){
                if(this.listaQuickpass[i]!=null&&this.listaQuickpass[i].getFilial().equals(filial)){
                    this.listaQuickpass[i].setEstado(nuevoEstado);
                    return true;
                }
            }
        
    }
        return false;
    }
    
    
        
    //Eliminar quickpass por codigo. No hace registro historial. 0=correcto, 1=fallo, 2= no encontrado
    public int eliminarQuickpassPorCodigo(String codigo){
        if(cant>0){
                                    
            for(int i=0;i<this.listaQuickpass.length;i++){
                if(this.listaQuickpass[i]!=null&&this.listaQuickpass[i].getCodigo().equals(codigo)){
                    JOptionPane.showMessageDialog(null,"Eliminacion por codigo:\n"
                        +"Quickpass eliminados previamente:" + Arrays.toString(this.quickEliminado)+"\n"
                        +"Quickpass funcionales ANTES de la eliminacion:" + Arrays.toString(this.listaQuickpass));
                    if(this.insertarQuickpassEliminado(this.listaQuickpass[i]))//lo manda a la lista de eliminados
                    {
                        this.listaQuickpass[i]= null;
                        cant--;
                        JOptionPane.showMessageDialog(null,"Eliminacion por codigo:\n"
                                +"Lista quickpass eliminados:" + Arrays.toString(this.quickEliminado)+"\n"
                                +"Lista quickpass funcionales despues de la eliminacion:" + Arrays.toString(this.listaQuickpass));
                        return 0;
                    }
                    else return 1;
                }
            } 
            return 2;
        }
        return 1;
    }
    
    //Eliminar quickpass por placa. No hace registro historial. 0=correcto, 1=fallo, 2= no encontrado 
    public int eliminarQuickpassPorPlaca(String placa){
        if(cant>0){
            
            for(int i=0;i<this.listaQuickpass.length-1;i++){
                if(this.listaQuickpass[i]!=null&&this.listaQuickpass[i].getPlaca().equals(placa)){
                    JOptionPane.showMessageDialog(null,"Eliminacion por placa:\n"
                        +"Quickpass eliminados previamente:" + Arrays.toString(this.quickEliminado)+"\n"
                        +"Quickpass funcionales ANTES de la eliminacion:" + Arrays.toString(this.listaQuickpass));
                    if(this.insertarQuickpassEliminado(this.listaQuickpass[i]))//lo manda a la lista de eliminados
                    {
                        this.listaQuickpass[i]= null;
                        cant--;
                        JOptionPane.showMessageDialog(null,"Eliminacion por placa:\n"
                                +"Lista quickpass eliminados:" + Arrays.toString(this.quickEliminado)+"\n"
                                +"Lista quickpass funcionales despues de la eliminacion:" + Arrays.toString(this.listaQuickpass));
                        return 0;
                    }
                    else return 1;
                }
            }
            return 2;
        }
        return 1;
    }
    
    
    
    //Consultar filial
    public String consultarQuickpass(String filial) {
        if(cant>0){
            for(int i=0;i<this.listaQuickpass.length;i++){
                if(this.listaQuickpass[i]!=null&&this.listaQuickpass[i].getFilial().equals(filial)){
                    return "Acceso permitido: Filial: "+ this.listaQuickpass[i].getFilial()+", Código: "
                            +this.listaQuickpass[i].getCodigo()+", Placa: "+this.listaQuickpass[i].getPlaca()
                            +", Estado: "+this.listaQuickpass[i].getEstado();
                }
            }
            return "Acceso denegado: Quickpass no encontrado.";
        }else{
            return "Lista vacía.";
        }
    }
    
    public int TotalQuickpass(){
        return cant;
    }
    
    public int consultarQuickEliminados(){
        return cantElim;
    }
    
    public int consultarQuickInactivos(){
        return quickInactivos;
    }
    
    
    //REVISAR
    public boolean transferirQuickpass(int indice) {
    // Verificar si el índice es válido
        if (indice < 0 || indice >= cant) {
            return false;
        }

    // Obtener el Quickpass a transferir
    Quickpass quickpassATransferir = listaQuickpass[indice];

    // Eliminar el Quickpass de la lista actual
        for (int i = indice; i < cant - 1; i++) {
            listaQuickpass[i] = listaQuickpass[i + 1];
        }
        listaQuickpass[cant - 1] = null;
        cant--;

        // Agregar el Quickpass a la lista de eliminados
        if (!insertarQuickpassEliminado(quickpassATransferir)) {
            // Manejar el caso en que no se pudo agregar a la lista de eliminados
            // Por ejemplo, podría devolver false o lanzar una excepción
            return false;
        }

        // Actualizar contadores (si es necesario)
        // ...

        return true;
    }
    
    
    @Override
    public String toString(){
        String r="";
        if(cant!=0){
            for(int i=0; i<this.listaQuickpass.length;i++){
                r+=this.listaQuickpass[i]+"\n";
            }
        } else{
        return r="Vacia";
        }
        return r;
    }
    
    
    
    
}
