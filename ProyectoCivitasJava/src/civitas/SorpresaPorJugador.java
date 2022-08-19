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
public class SorpresaPorJugador extends Sorpresa {

    private int valor;

    SorpresaPorJugador(int valor, String texto) {
        super(texto);
        this.valor = valor;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList <Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            Sorpresa pagar = new SorpresaPagarCobrar(valor*-1, "Pagar");
            Sorpresa cobrar = new SorpresaPagarCobrar(valor*(todos.size()-1), "Cobrar");
            for(int k = 0; k < todos.size(); k++){
                if(k != actual)
                    pagar.aplicarAJugador(k, todos); 
                else
                    cobrar.aplicarAJugador(k, todos);
            }
        
        }        
    }
    
    @Override
    public String toString(){
        String salida = "Sorpresa " +  valor + " Por Jugador";
        return salida;
    }
    
}
