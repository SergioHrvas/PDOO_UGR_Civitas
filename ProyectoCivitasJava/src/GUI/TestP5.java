/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.util.ArrayList;
import civitas.CivitasJuego;
/**
 *
 * @author sergio
 */
public class TestP5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CivitasView vista = new CivitasView();
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(Boolean.FALSE);
        CapturaNombres captura = new CapturaNombres(vista, true);
        ArrayList lista;
        lista = captura.getNombres();
        CivitasJuego juego = new CivitasJuego(lista);
        Controlador control = new Controlador(juego, vista);
        vista.setCivitasJuego(juego);
        control.juega();
        // TODO code application logic here
    }
    
}
