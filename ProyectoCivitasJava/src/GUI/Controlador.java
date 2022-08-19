/**
 * Proyecto Civitas
 * Sergio Hervás Cobo
 * Javier Suárez Lloréns
 * Programación y Diseño Orientado a Objetos
 * 2º B3 - Grado en Ingeniería Informática
 * E.T.S.I. Informática y de Telecomunicación
 * Universidad de Granada
 */
package GUI;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.Jugador;
import java.util.ArrayList;
import civitas.OperacionInmobiliaria;
import civitas.GestionesInmobiliarias;
import civitas.SalidasCarcel;

class Controlador {
    private CivitasJuego juego;
    private CivitasView vista;
    
    Controlador(CivitasJuego juego, CivitasView vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        
        while(!juego.finalDelJuego()){
            
            vista.actualizarVista();
            
            //vista.pausa();
            
            OperacionesJuego siguienteop = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(siguienteop);
            
            if (siguienteop != OperacionesJuego.PASAR_TURNO)
                vista.mostrarEventos();
            
            boolean esElFinal = juego.finalDelJuego();
            
            if (!esElFinal){
                
                switch(siguienteop){
                    case COMPRAR:
                        Respuestas resp = vista.comprar();
                        if(resp == Respuestas.SI){
                            juego.comprar();                    
                        }
                        juego.siguientePasoCompletado(siguienteop); 
                        
                break;
                case GESTIONAR:
                    vista.gestionar();
                    int gestion = vista.getGestion();
                    int propiedad = vista.getPropiedad();
                    
                    OperacionInmobiliaria operInmo = new OperacionInmobiliaria(GestionesInmobiliarias.values()[gestion], propiedad);

                        if (operInmo.getGestiones() == GestionesInmobiliarias.CANCELAR_HIPOTECA){
                            juego.cancelarHipoteca(propiedad);
                        }
                        else if (operInmo.getGestiones() == GestionesInmobiliarias.VENDER){
                            juego.vender(propiedad);
                        }
                        else if (operInmo.getGestiones() == GestionesInmobiliarias.HIPOTECAR){
                            juego.hipotecar(propiedad);
                        }
                        else if (operInmo.getGestiones() == GestionesInmobiliarias.CONSTRUIR_CASA){
                            juego.construirCasa(propiedad);
                        }
                        else if (operInmo.getGestiones() == GestionesInmobiliarias.CONSTRUIR_HOTEL){
                            juego.construirHotel(propiedad);
                        }
                        else if(operInmo.getGestiones() == GestionesInmobiliarias.TERMINAR){
                            juego.siguientePasoCompletado(siguienteop);
                        }     
                break;          
                case SALIR_CARCEL:
                            SalidasCarcel salida = vista.salirCarcel();
                            if (salida == SalidasCarcel.PAGANDO){
                                juego.salirCarcelPagando();
                            }
                            else if(salida == SalidasCarcel.TIRANDO){
                                juego.salirCarcelTirando();
                            }
                            juego.siguientePasoCompletado(siguienteop);
                break;
                            
                }
            }

            else{
                //Ranking ahora tiene la visibilidad pública
                ArrayList<Jugador> top = juego.ranking();
                int puesto = 0;
                Diario.getInstance().ocurreEvento("\n\nUn jugador ha caído en la bancarrota.\n\n");
                Diario.getInstance().ocurreEvento("\n#####--RANKING--#####");
                for ( int k = 0; k < top.size(); k++){
                    puesto++;
                    Diario.getInstance().ocurreEvento("TOP: " + puesto + ":  " + top.get(k).toString());
                }
                vista.actualizarVista();
            }
        }
    }      
  }
