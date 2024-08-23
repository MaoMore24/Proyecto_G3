
package proy_final_g3;


import javax.swing.JOptionPane;


public class Quickpass {
    //Atributos
    private String filial, placa, codigo;
    private Estado Estado;
    
    
    
    //Métodos
    //Constructor
    public Quickpass(){
        }
    
    public Quickpass (String pFilial, String pCodigo, String pPlaca, Estado pEstado){
        this.filial = pFilial;
        this.codigo = pCodigo;
        this.placa = pPlaca; 
        this.Estado = pEstado;        
    }

    
    //filial
    public void setFilial(String pFilial) {
        this.filial =pFilial;
        }
    
    public String getFilial(){
        return this.filial;
    }
    
           
        
    //Estado
    public void setEstado(Estado pEstado) {
        this.Estado = pEstado;
    }
    
    public Estado getEstado(){
        return Estado;
    }
    
    public void nuevoEstado(Estado nuevoEstado){
        String respuesta=JOptionPane.showInputDialog("Digite el nuevo estado: (A/I)");
        if(respuesta.equals("A")){
            JOptionPane.showMessageDialog(null,"El estado es activo.");
        }else if(respuesta.equals("I")){
            nuevoEstado = Estado.Inactivo;
            this.Estado = nuevoEstado;
            JOptionPane.showMessageDialog(null,"El estado es Inactivo.");
        }
        
    }
    
    
    //Código
    public void setCodigo(String pCodigo) {
        this.codigo = pCodigo;
    }
    
    public String getCodigo(){
        return this.codigo;
    }
    
       
    //Placa
    public void setPlaca(String pPlaca) {
        this.placa = pPlaca;
    }
    
    public String getPlaca(){
        return this.placa;
    }
    
    //Consultar la información almacenada
    @Override
    public String toString(){
        return filial + "," + codigo+ "," + placa + "," + Estado;
    }
    
}
    
      
    
