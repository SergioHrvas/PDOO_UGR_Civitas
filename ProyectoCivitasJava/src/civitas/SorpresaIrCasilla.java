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
public class SorpresaIrCasilla extends Sorpresa {

    private int valor;
    private Tablero tablero;

    SorpresaIrCasilla(Tablero tablero, int valor, String texto) {
        super(texto);
        this.tablero = tablero;
        this.valor = valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        int casillaActual, tirada, posicion;
        if (jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            casillaActual = todos.get(actual).getNumCasillaActual();
            tirada = tablero.calcularTirada(casillaActual, valor);
            posicion = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(posicion);
            tablero.getCasilla(posicion).recibeJugador(actual, todos);
        }
    }
   
    @Override
    public String toString(){
        String salida = "Sorpresa Ir A Casilla " + valor;
        return salida;
    }
}
