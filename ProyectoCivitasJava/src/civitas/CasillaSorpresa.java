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
public class CasillaSorpresa extends Casilla {
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        this.mazo = mazo;
    }
    

    @Override
    void recibeJugador(int iactual, ArrayList <Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            sorpresa = mazo.siguiente();
            informe(iactual, todos);
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    
    @Override
    public String toString(){
        String salida = "Casilla " + getNombre();
        
        
        return salida;
    }
}
