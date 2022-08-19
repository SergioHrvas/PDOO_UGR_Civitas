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

public class Tablero {
    
    //Atributos de instancia:
    private int numCasillaCarcel;
    private int porSalida;
    private boolean tieneJuez;

    //Atributo de referencia
    private ArrayList <Casilla> casillas;
    
    //Atributo de clase: (para evitar números mágicos)
    private static final int NUM_CASILLAS = 20;
    
    //Constructor
    Tablero(int indice){
        numCasillaCarcel = 1;
        if(indice>=1)
            numCasillaCarcel = indice;

        
        porSalida = 0;
        tieneJuez = false;
        
        casillas = new ArrayList<>();
        casillas.add(new Casilla("Salida"));
            
    }
    
    
    //Métodos privados
    
    private boolean correcto(){
        boolean comprobacion = false;
        if (casillas.size() > numCasillaCarcel && tieneJuez){
            comprobacion = true;
        }
       return comprobacion;
        
    }
    
    private boolean correcto(int numCasilla){
        boolean comprobacion = false;
        if(correcto() && numCasilla < casillas.size() && numCasilla >= 0){
            comprobacion = true;

        }
    
        return comprobacion;
    }
    
    //Consultores
    
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    int getPorSalida(){
        if(porSalida > 0){
            porSalida--;
            return porSalida+1;
        }
        else{
            return porSalida;
        }
    }
    
        
    Casilla getCasilla (int numCasilla){
        if(correcto(numCasilla)){
            return casillas.get(numCasilla); //VER SI ES -1 O NO
        }
        return null;
    }
    
    //Resto de métodos
    void añadeCasilla(Casilla casilla){
        Casilla carcel = new Casilla("Cárcel");
        añadeCarcel(carcel);
        casillas.add(casilla);
        añadeCarcel(carcel); //Verifica de nuevo
        
    }
    
    
    //Método privado añadido por mi para simplificar añadeCasilla
    private void añadeCarcel(Casilla carcel){
        if(casillas.size()==numCasillaCarcel)
            casillas.add(carcel);
    }
    
    
    void añadeJuez(){
        if(!tieneJuez){
            añadeCasilla(new CasillaJuez(numCasillaCarcel, "Juez"));
            tieneJuez=true;
        }
    }

    
    int nuevaPosicion(int actual, int tirada){
        int posicion = -1;

        if (correcto()){
            posicion = ( actual + tirada );
            if(posicion >= casillas.size())

            porSalida++;
        }
        
        return posicion % NUM_CASILLAS;
    }
    
    int calcularTirada(int origen, int destino){
        int tirada = destino - origen;
        if (tirada < 0)
            tirada=tirada+NUM_CASILLAS;
        
        return tirada;
        }
}

