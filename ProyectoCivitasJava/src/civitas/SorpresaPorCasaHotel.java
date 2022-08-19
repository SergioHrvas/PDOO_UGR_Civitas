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
public class SorpresaPorCasaHotel extends Sorpresa {
    private String texto;
    private int valor;

    SorpresaPorCasaHotel(int valor, String texto) {
        super(texto);
        this.valor = valor;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList <Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());
        }        
    }
    
    @Override
    public String toString(){
        String salida = "Sorpresa " + valor + " por casa/hotel";
        return salida;
    }
    
    
}
