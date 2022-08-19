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
public class CasillaImpuesto extends Casilla {

    private float importe;

    CasillaImpuesto(float cantidad, String nombre) {
        super(nombre);
        this.importe = cantidad;
    }

    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(iactual, todos)) {
            informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
    
    public float getImporte(){
        return importe;
    }
    
    @Override
    public String toString(){
        String salida = "Casilla " + getNombre() + " | Cantidad: " + importe;
        
        return salida;
    }
}
