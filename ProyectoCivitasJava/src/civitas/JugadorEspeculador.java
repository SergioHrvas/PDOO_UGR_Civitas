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

/**
 *
 * @author sergio
 */
public class JugadorEspeculador extends Jugador {
    
    private static final int factorEspeculador = 2;
    private int fianza;
    
    
    public JugadorEspeculador(Jugador otro, int fianza){
        super(otro);
        this.fianza = fianza;
        for (int k = 0; k < otro.getPropiedades().size(); k++){
            otro.getPropiedades().get(k).actualizaPropietarioPorConversion(this);
        }
    }
    
    
    @Override
    protected boolean debeSerEncarcelado(){
        boolean debeserlo = super.debeSerEncarcelado();
        if(debeserlo && puedoGastar(fianza)){
            debeserlo = false;
            paga(fianza);
            Diario.getInstance().ocurreEvento(getNombre() + " paga la fianza y se libra de la cárcel");
        }
        return debeserlo;
    }
   
    
    @Override
    boolean pagaImpuesto(float cantidad){
        boolean comprobacion = false;
        if(!isEncarcelado()){
            comprobacion = paga(cantidad/2);
        }
        return comprobacion;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puedoedificarcasa = false;
        float precio = propiedad.getPrecioEdificar();
        
        if (super.puedoGastar(precio) && propiedad.getNumCasas() < this.getCasasMax()){
            puedoedificarcasa = true;
        }
        return puedoedificarcasa;
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean puedoedificarhotel = false;
        float precio = propiedad.getPrecioEdificar();
        if ( puedoGastar(precio) && propiedad.getNumHoteles() < this.getHotelesMax() && propiedad.getNumCasas() >= this.getCasasPorHotel()){
            puedoedificarhotel = true;
        }
        return puedoedificarhotel;
    }        
    
    @Override
    protected int getHotelesMax(){
        return super.getHotelesMax()*factorEspeculador;
    }

    @Override
    protected int getCasasMax(){
        return super.getCasasMax()*factorEspeculador;
    }
    
    @Override
    public String toString(){
      String salida = "Jugador Especulador: " + super.toString();
      return salida;
    } 
}
