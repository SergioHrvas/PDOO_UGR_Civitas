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
public class SorpresaPagarCobrar extends Sorpresa {

    private int valor;

    SorpresaPagarCobrar(int valor, String texto) {
        super(texto);
        this.valor = valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }

    }
    
    @Override
    public String toString(){
        String salida = "Sorpresa " + valor + " Pagar/Cobrar";
        return salida;
    }
}
