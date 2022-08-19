/**
 * Proyecto Civitas
 * Sergio Hervás Cobo
 * Javier Suárez Lloréns
 * Programación y Diseño Orientado a Objetos
 * 2º B3- Grado en Ingeniería Informática
 * E.T.S.I. Informática y de Telecomunicación
 * Universidad de Granada
 */
package civitas;

import GUI.Dado;
import java.util.ArrayList;

public class Jugador implements Comparable<Jugador> {
    
    //ATRIBUTOS DE CLASE
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static float PasoPorSalida = 1000f;
    protected static float PrecioLibertad = 200f;
    protected static int HotelesMax = 4;
    private static float  SaldoInicial = 7500f;
    
    //ATRIBUTOS DE INSTANCIA
    private String nombre;
    private int numCasillaActual;
    protected boolean encarcelado;
    private boolean puedeComprar;
    private float saldo;
    
    //ATRIBUTOS DE REFERENCIA
    private ArrayList <TituloPropiedad> propiedades;
    private SorpresaSalirCarcel salvoconducto;
    
    
    Jugador(String nombre){
        this.nombre = nombre;
        puedeComprar = false;
        saldo = SaldoInicial;
        numCasillaActual = 0;
        encarcelado = false;
        propiedades = new ArrayList<>();   
        salvoconducto = null;
    }
    
    protected Jugador (Jugador otro){
        
        salvoconducto = otro.salvoconducto;
        nombre = otro.nombre;
        puedeComprar = otro.puedeComprar;
        saldo = otro.saldo;
        numCasillaActual = otro.numCasillaActual;
        encarcelado = otro.encarcelado;
        propiedades = otro.propiedades;
        
    }
    
    
    boolean cancelarHipoteca (int ip){
     boolean result = false;
     if (!isEncarcelado()){
         if (existeLaPropiedad(ip)){
             TituloPropiedad propiedad = propiedades.get(ip);
             float cantidad = propiedad.getImporteCancelarHipoteca();
             boolean puedoGastar = puedoGastar(cantidad);
             
             if(puedoGastar){
                 result = propiedad.cancelarHipoteca(this);
                 
                 if(result)
                     Diario.getInstance().ocurreEvento(nombre + " cancelar la hipoteca de la propiedad " + ip + "\n" + propiedades.get(ip)+ "\n");
                 
             }
        }
     }
     return result;
    }
    
    
    int cantidadCasasHoteles(){
        int numero = 0;
        for (int i = 0; i < propiedades.size(); i++)
            numero += propiedades.get(i).cantidadCasasHoteles();
        return numero;
    }
    
    
    @Override
    public int compareTo(Jugador otro){
        return Float.compare(saldo, otro.getSaldo());
    }
    
    
    
    
    boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        
        if(!encarcelado){
            if (puedeComprar){
                float precio = titulo.getPrecioCompra();
                
                if (puedoGastar(precio)){
                    result = titulo.comprar(this);
                    if (result){               
                        propiedades.add(titulo);
                        Diario.getInstance().ocurreEvento(this + " compra la propiedad " + titulo.getNombre() + "\n" + titulo + "\n");               
                    }
                    puedeComprar = false;
                }
            }
        }
        return result;
    }
    
    
    boolean construirCasa (int ip){
        boolean result = false;
        boolean puedoEdificarCasa = false;
        if (!encarcelado){
            boolean existe = existeLaPropiedad(ip);
            if (existe){
                TituloPropiedad propiedad = propiedades.get(ip);
                puedoEdificarCasa = puedoEdificarCasa(propiedad);
                if(puedoEdificarCasa){
                    result = propiedad.construirCasa(this);
                    if(result){
                        Diario.getInstance().ocurreEvento(nombre + " construye casa en la propiedad " + ip + "\n" + propiedades.get(ip)+ "\n");
                    }
                } 
            }
        } 
        return result;
    }
    
    
    boolean construirHotel(int ip){
        boolean result = false;
        if (!encarcelado){
            if(existeLaPropiedad(ip)){
             TituloPropiedad propiedad = propiedades.get(ip);
             boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
             float precio = propiedad.getPrecioEdificar();
             
             if(puedoGastar(precio)){
                 if(propiedad.getNumHoteles()<getHotelesMax()){
                     if (propiedad.getNumCasas() >= getCasasPorHotel()){
                         puedoEdificarHotel = true;
                         
                     }
                 }
             }
             
             if(puedoEdificarHotel){
                 result = propiedad.construirHotel(this);
                 int casasPorHotel = getCasasPorHotel();
                 propiedad.derruirCasas(casasPorHotel, this);
             }
                
                
            }            
        }
        
        if (result) {
            Diario.getInstance().ocurreEvento(nombre + " construye hotel en la propiedad " + ip + "\n" + propiedades.get(ip)+ "\n");
        }
        return result;
    }
    
    
    protected boolean debeSerEncarcelado(){
        boolean debeserlo = false;
        if(!isEncarcelado()){
            if(!tieneSalvoconducto()){
                debeserlo = true;
            }
            else{
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento(nombre + " se libra de la cárcel");
            }
        }
        return debeserlo;
    }
    
    boolean enBancarrota(){
       boolean comprobacion = false;
       if(saldo <= 0)
           comprobacion = true;
       return comprobacion;
        
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento(nombre + " ha sido encarcelado.");

        }
        return encarcelado;
        
    }
    
    private boolean existeLaPropiedad(int ip){
        return (ip>=0&&ip<propiedades.size());
    }
    
    
    //visibilidad cambiada a protected
    protected int getCasasMax(){
        return CasasMax;
    }
    
    
    //visibilidad cambiada a protected
    protected int getHotelesMax(){
        return HotelesMax;
    }
    
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    //cambiada visibilidad a publico
    public String getNombre(){ 
        return nombre;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    private float getPremioPasoSalida(){
        return PasoPorSalida;
    }

    
    public ArrayList<TituloPropiedad> getPropiedades(){ //Debe ser publico para el método gestionar de VistaTextual
        return propiedades;
        
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;        
    }
    
        //cambiada visibilidad a publico
    public float getSaldo(){
        return saldo;
    }
    
    
    boolean hipotecar(int ip){
        boolean result = false;
        
        if(!encarcelado){
            if (existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                result = propiedad.hipotecar(this);
                
                
            }
            
            if (result){
                Diario.getInstance().ocurreEvento(nombre + " hipoteca la propiedad " + ip + "\n" + propiedades.get(ip)+ "\n");
            }
        }
        return result;
    }
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    boolean modificarSaldo(float cantidad){
        saldo = saldo+cantidad;
        String evento;
        
        if (cantidad < 0){
            float devuelve = cantidad*(-2) + cantidad;
            evento = this + " pierde " + devuelve + ". ";
        } else {
            evento = this + " gana " + cantidad + ". ";
        }
        Diario.getInstance().ocurreEvento(evento);
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("Te mueves a la casilla: " + numCasilla);
            comprobacion = true;
        }
        return comprobacion;
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            salvoconducto = sorpresa;
            comprobacion = true;
        }
        return comprobacion;
        
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(cantidad*-1);
        }
    
    boolean pagaAlquiler(float cantidad){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            comprobacion = paga(cantidad);
        }
        return comprobacion;        
    }
    
    boolean pagaImpuesto(float cantidad){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            comprobacion = paga(cantidad);
        }
        
        return comprobacion;
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(getPremioPasoSalida());
        Diario.getInstance().ocurreEvento("El jugador ha pasado por la salida.");
        return true;
    }
    
   private void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto = null;     
    }
    
    boolean puedeComprarCasilla(){
       puedeComprar = true;
        if(isEncarcelado()){
           puedeComprar = false;
       }
       return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        boolean comprobacion = false;
        if(saldo >= getPrecioLibertad()){
            comprobacion = true;
        }
        return comprobacion;
    }
    
    
    //visibilidad cambiada a protected
    protected boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puedoedificarcasa = false;
        float precio = propiedad.getPrecioEdificar();
        
        if (puedoGastar(precio) && propiedad.getNumCasas() < getCasasMax()){
            puedoedificarcasa = true;
        }
        return puedoedificarcasa;
    }
    
    
    //visibilidad cambiada a protected
    protected boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean puedoedificarhotel = false;
        float precio = propiedad.getPrecioEdificar();
        if ( puedoGastar(precio) && propiedad.getNumHoteles() < getHotelesMax() && propiedad.getNumCasas() >= getCasasPorHotel()){
            puedoedificarhotel = true;
        }
        return puedoedificarhotel;
    }        
    
    //visibilidad cambiada a protected
    protected boolean puedoGastar(float precio){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            comprobacion = saldo >= precio;
            
        }
        return comprobacion;
    }
    
    boolean recibe(float cantidad){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            comprobacion = modificarSaldo(cantidad);
        }
        return comprobacion;
    }
    
    
    boolean salirCarcelPagando(){
        boolean comprobacion = false;
        if(isEncarcelado() && puedeSalirCarcelPagando()){
            paga(getPrecioLibertad());
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador ha salido de la carcel pagando");
            comprobacion = true;
        }
        return comprobacion;        
    }
    
    boolean salirCarcelTirando(){
        boolean comprobacion = false;
        if(Dado.getInstance().salgoDeLaCarcel()){
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador ha salido de la carcel tirando");
            comprobacion = true;
        }
        return comprobacion;
    }
    
    boolean tieneAlgoQueGestionar(){
        return propiedades.size()>0;
    }
    
    boolean tieneSalvoconducto(){
        return salvoconducto != null;  
    }
    
    @Override
    public String toString(){
      String salida = nombre + " ( " + saldo + " )" + " en la casilla " + numCasillaActual;
      
      return salida;
    } 
    
    boolean vender (int ip){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            if(existeLaPropiedad(ip)){
                if(propiedades.get(ip).vender(this)){
                    Diario.getInstance().ocurreEvento("Se ha vendido la propiedad " + propiedades.get(ip).getNombre()+ "\n");
                    propiedades.remove(ip);
                    comprobacion = true;
                }
              
            }
     
        }
        return comprobacion;
    }
}


   