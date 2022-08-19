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
public class CasillaJuez extends Casilla {

    private static int carcel;

    CasillaJuez(int carcel, String nombre) {
        super(nombre);
        this.carcel = carcel;
    }

    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(iactual, todos)) {
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
    
    @Override
    public String toString(){
        String salida = "Casilla " + getNombre();        
        return salida;      
    }

}
