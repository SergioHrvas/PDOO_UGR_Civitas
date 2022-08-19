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
public class SorpresaIrCarcel extends Sorpresa {

    private Tablero tablero;

    SorpresaIrCarcel(Tablero tablero) {
        super("Ir a la carcel");
        this.tablero = tablero;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    @Override
    public String toString(){
        String salida =  "Sorpresa para ir a la carcel";
        return salida;
    }
}
