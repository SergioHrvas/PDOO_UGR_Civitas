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

public class Casilla {
    
    //Atributos de instancia
    private String nombre;  
    
    Casilla(String nombre){ //constructor de la clase    
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    protected void informe(int iactual, ArrayList <Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " ha caído en la Casilla: " + toString());
    }
    
    public boolean jugadorCorrecto(int iactual, ArrayList <Jugador> todos){
        boolean comprobacion = false;
        if(iactual >= 0 && iactual < todos.size()){
            comprobacion = true;
        }
        return comprobacion;
    }
    
    void recibeJugador(int iactual, ArrayList <Jugador> todos){
        if(jugadorCorrecto(iactual, todos))
            informe(iactual, todos);
    }
    
    @Override
    public String toString(){
        String salida = nombre;

        return salida;
    }
}
