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

import GUI.Dado;
import java.util.ArrayList;
import java.util.Collections;

public class CivitasJuego {
    
    private final static int POSICION_CARCEL = 5;
    //Atributos de instancia
    private int indiceJugadorActual;
    
    //Atributos de referencia
    private Tablero tablero;
    private MazoSorpresas mazo;
    private EstadosJuego estado;
    private ArrayList <Jugador> jugadores;
    private GestorEstados gestorEstados;
    
    //Métodos       
    public CivitasJuego(ArrayList <String> nombres){
        //Inicializamos el ArrayList jugadores y 
        //asignamos los jugadores de la partida y sus nombres
        jugadores = new ArrayList<>(); 
        for (int k = 0; k < nombres.size(); k++){
          jugadores.add(new Jugador(nombres.get(k)));
        }
        
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        
        mazo = new MazoSorpresas(false);

        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);
   
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadores.get(indiceJugadorActual));
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadores.get(indiceJugadorActual));
    }
    
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    public boolean comprar(){

        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        CasillaCalle casilla = (CasillaCalle) tablero.getCasilla(numCasillaActual); //Casting
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        
        return res;
        
    }
    
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);   
    }
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip); 
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida()>0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    public boolean finalDelJuego(){
        boolean comprobacion = false;
        for(int k = 0; k < jugadores.size(); k++){
            if(jugadores.get(k).enBancarrota())
                comprobacion = true;
        }
        return comprobacion;
    }
    
    public Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
        
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);  
    }
    
    public boolean hipotecar(int ip){
       return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        return jugadores.get(indiceJugadorActual).toString();
        
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        mazo.alMazo(new SorpresaIrCarcel(tablero));
        mazo.alMazo(new SorpresaIrCasilla(tablero, POSICION_CARCEL, "Dirígete a la carcel de visita"));
        mazo.alMazo(new SorpresaIrCasilla(tablero, 12, "Debes pagar tus impuestos. Dirígete a la casilla 12."));
        mazo.alMazo(new SorpresaIrCasilla(tablero, 18, "Están preguntando por ti en las oficinas de Recogidas. Dirigete a la casilla 18"));
        mazo.alMazo(new SorpresaPagarCobrar( 500, "Has ganado la lotería. Recibes 500"));
        mazo.alMazo(new SorpresaPagarCobrar( -500, "Debes devolver 500 a hacienda."));
        mazo.alMazo(new SorpresaPorCasaHotel( 25, "Tus bienes se han revalorizado. Recibes 25 por cada construcción."));
        mazo.alMazo(new SorpresaPorCasaHotel(-25, "Debes pagar 25 por cada construcción debido a reparaciones y obras"));
        mazo.alMazo(new SorpresaPorJugador( 100, "Es tu cumpleaños, recibes 100 de cada jugador"));
        mazo.alMazo(new SorpresaPorJugador( -60, "Tienes deudas pendientes. Das 60 a cada jugador"));
        mazo.alMazo(new SorpresaSalirCarcel( mazo));
        mazo.alMazo(new SorpresaEspeculador( 500));
    
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        tablero = new Tablero(POSICION_CARCEL);
        //Ya está agregada la Salida en la posición 0
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Gonzalo Gallas",          75,  1.05f, 400 , 500 , 350  )));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Severo Ochoa",            90,  1.1f,  448 , 560 , 450  )));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa 1"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Elvira",                  105, 1.1f,  504 , 630 , 550  )));
        //Aquí se añade la Cárcel
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Méndez Nuñez",            120, 1.2f,  568 , 710 , 650  )));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa 2"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Camino de Ronda",               135, 1.3f,  640 , 800 , 750  )));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Acera del Darro",         150, 1.3f,  720 , 900 , 850  )));
        tablero.añadeCasilla(new Casilla("Parking"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Gran Vía de Colón",             165, 1.4f,  808 , 1010, 950  )));
        tablero.añadeCasilla(new CasillaImpuesto(250f,"Impuesto de luz"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Ángel Ganivet",           180, 1.5f,  904 , 1130, 1050 )));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Reyes Católicos",         195, 1.6f,  1008, 1260, 1150 )));
        tablero.añadeJuez();
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Avenida de la Constitución",    210, 1.7f,  1120, 1400, 1250 )));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa 3"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle Recogidas",               225, 1.8f,  1240, 1550, 1350 )));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Paseo de los Tristes",          240, 2.0f,  1368, 1710, 1450 )));
        
    }
    
    private void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual+1)%jugadores.size();
        
    }
    
    
    public ArrayList <Jugador> ranking(){
        
        //Creamos un nuevo ArrayList de jugadores para ordenarlos
        ArrayList <Jugador> ranking;
        ranking = new ArrayList<>();
        for(int i = 0; i < jugadores.size();i++){
            ranking.add(jugadores.get(i));
        }
        
        //Llamamos al método de comparar de la clase Jugador mediante Collections
        Collections.sort(ranking, Collections.reverseOrder());
        return ranking;
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
        
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        
        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if (operacion == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
        
    }
    
    public boolean vender (int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
        
    }
    
    
    
}
