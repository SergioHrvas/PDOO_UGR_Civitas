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

public class OperacionInmobiliaria {
    private GestionesInmobiliarias gestiones;
    private int indicePropiedad;
    
    public OperacionInmobiliaria(GestionesInmobiliarias gestiones, int indice){
        this.gestiones = gestiones;
        indicePropiedad = indice;
    }
    
    public GestionesInmobiliarias getGestiones(){
        return gestiones;
    }
    
    int getIndicePropiedad(){
        return indicePropiedad;
    }
    
    
}
