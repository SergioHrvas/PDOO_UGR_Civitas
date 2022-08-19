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
import java.util.Collections;

public class MazoSorpresas {
    
    //Atributos de instancia
    private boolean barajada;
    private int usadas; 
    private boolean debug;
    private Sorpresa ultimaSorpresa;
    
    //Atributos de referencia
    public ArrayList <Sorpresa> sorpresas;
    private ArrayList <Sorpresa> cartasEspeciales;

    private void init(){
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }
    
    MazoSorpresas(boolean d){
        debug = d;
        init();
        if (debug){
            Diario.getInstance().ocurreEvento("MazoSorpresas en modo debug");
        }
    }
    
    MazoSorpresas(){
        init();
        debug = false;
    }
    
    void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    Sorpresa siguiente(){
        if(!barajada || usadas==sorpresas.size()){
            if(!debug){
                Collections.shuffle(sorpresas);
            }
            usadas = 0;
            barajada = true;
        }
        usadas++;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);
     
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.contains(sorpresa)){
            sorpresas.remove(sorpresa);
            cartasEspeciales.add(sorpresa);       
            Diario.getInstance().ocurreEvento("Carta Especial inhabilitada.");
        }
        

    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        if(cartasEspeciales.contains(sorpresa)){
            cartasEspeciales.remove(sorpresa);
            sorpresas.add(sorpresa);
            Diario.getInstance().ocurreEvento("Carta Especial habilitada");
        }        
    }
    
    Sorpresa getUltimaSorpresa(){
        return ultimaSorpresa;
    }
}
