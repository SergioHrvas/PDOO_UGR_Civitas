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
public class SorpresaSalirCarcel extends Sorpresa {

    private MazoSorpresas mazo;

    SorpresaSalirCarcel(MazoSorpresas mazo) {
        super("Salir de carcel");
        this.mazo = mazo;
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            boolean comprobacion = false;
            informe(actual, todos);
            for (int k = 0; k < todos.size(); k++) {
                if (todos.get(k).tieneSalvoconducto()) {
                    comprobacion = true;
                }
            }
            if (!comprobacion) {
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }

    void usada() {
        mazo.habilitarCartaEspecial(this);
    }

    void salirDelMazo() {
        mazo.inhabilitarCartaEspecial(this);
    }

    @Override
    public String toString(){
        String salida = "Sorpresa para salir de la cárcel";
        return salida;
    }
    
    
}
