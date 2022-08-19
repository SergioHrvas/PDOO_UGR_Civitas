/**
 * Proyecto Civitas
 * Sergio Hervás Cobo
 * Javier Suárez Lloréns
 * Programación y Diseño Orientado a Objetos
 * 2º B3 - Grado en Ingeniería Informática
 * E.T.S.I. Informática y de Telecomunicación
 * Universidad de Granada
 */
package civitas;

import java.util.ArrayList;

abstract class Sorpresa {
    
    protected String texto;
   
    Sorpresa(String texto){
        this.texto = texto;
    }
      
    
    //cambio visibilidad a protected
    protected void informe (int actual, ArrayList <Jugador> todos){
        Diario.getInstance().ocurreEvento(texto + "\nAplicando sorpresa <" + toString() + "> al jugador " + todos.get(actual).getNombre() + "\n");
    }
    

    public boolean jugadorCorrecto(int actual, ArrayList todos){
        boolean comprobacion = false;
        if(actual >= 0 && actual < todos.size()){
            comprobacion = true;
        }
        return comprobacion;
    }
    
    @Override
    public String toString(){
        String salida = "Sorpresa Nula";
        return salida;
    }
    
        
    abstract void aplicarAJugador(int actual, ArrayList <Jugador> todos);

}
