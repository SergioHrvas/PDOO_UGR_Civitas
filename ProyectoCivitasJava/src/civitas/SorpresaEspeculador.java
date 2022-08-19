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
public class SorpresaEspeculador extends Sorpresa {

    private int fianza;

    SorpresaEspeculador(int cantidad) {
        super("Te conviertes en jugador especulador.");
        fianza = cantidad;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.set(actual,new JugadorEspeculador(todos.get(actual), fianza));
        }
    }
    
    @Override
    public String toString(){
        String salida = "Jugador -> Especulador";
        return salida;
    }
}
