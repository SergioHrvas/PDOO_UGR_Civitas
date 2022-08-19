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

/**
 *
 * @author sergio
 */
public class CasillaCalle extends Casilla {
    
    private TituloPropiedad tituloPropiedad;
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        this.tituloPropiedad = titulo;       
    }
   
    //visibilidad cambiada
    public TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }    
    
    @Override
    void recibeJugador(int iactual, ArrayList <Jugador> todos){
       if(jugadorCorrecto(iactual, todos)){
           informe(iactual, todos);
           Jugador jugador = todos.get(iactual);
           
           if (!tituloPropiedad.tienePropietario()){
               jugador.puedeComprarCasilla();
           }
           else{
               tituloPropiedad.tramitarAlquiler(jugador);
           } 
       }
    }
    
    @Override
    public String toString(){
        String salida = getNombre() + " | " + tituloPropiedad;
        return salida;
    }
    
}
