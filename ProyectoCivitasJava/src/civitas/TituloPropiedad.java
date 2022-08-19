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

public class TituloPropiedad {
    //Atributos de clase
    private static float factorInteresesHipoteca = 1.1f;
    
    //Atributos de instancia
    private float alquilerBase;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    //Atributos de referencia
    private Jugador propietario;
    
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){
        nombre = nom;
        alquilerBase = ab;
        factorInteresesHipoteca = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        propietario = null;
        numCasas = 0;
        numHoteles = 0;
        hipotecado = false;
    }
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean result = false;
        if (hipotecado){
            if(esEsteElPropietario(jugador)){
                jugador.paga(getImporteCancelarHipoteca());
                hipotecado = false;
                result = true;
            }
        }
        
        return result;
    }
    
    int cantidadCasasHoteles(){
        return numCasas+numHoteles;
    }
    
    boolean comprar(Jugador jugador){
        boolean result = false;
        if(!tienePropietario()){
            propietario = jugador;
            result = true;
            jugador.paga(getPrecioCompra());
            
        }
        return result;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean result = false;
        if(esEsteElPropietario(jugador)){
            jugador.paga(precioEdificar);
            numCasas = numCasas + 1;
            result = true;
        }
        return result;
    }
    
    boolean construirHotel(Jugador jugador){
        boolean result = false;
        if(esEsteElPropietario(jugador)){
            jugador.paga(precioEdificar);
            numHoteles = numHoteles + 1;
            result = true;
        }
        return result;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        boolean comprobacion = true;
        if (jugador == propietario && numCasas >= n){
            numCasas -= n;
        }
        else
            comprobacion = false;
        return comprobacion;
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        boolean comprobacion = true;
        if (propietario != jugador){
            comprobacion = false;
        }
        
        return comprobacion;
    }
    
    public boolean getHipotecado(){
        return hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        return factorInteresesHipoteca*(getImporteHipoteca());
    }
    
    private float getImporteHipoteca(){
        return hipotecaBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
    }
    
    public String getNombre(){ //Debe ser publico para el método gestionar de vistatextual
        return nombre;
    }
    
    //visibilidad cambiada
    public int getNumCasas(){
        return numCasas;
    }
    
    
    //visibilidad cambiada
    public int getNumHoteles(){
        return numHoteles;
    }
    
    //visibilidad cambiada
    public float getPrecioAlquiler(){
        float alquiler;
        if(hipotecado || propietarioEncarcelado())
            alquiler = 0;
        else
            alquiler = alquilerBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
    
    return alquiler;
    }
    
    //cambiar visibilidad
    public float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        return precioCompra+(numCasas+5*numHoteles)*precioEdificar*factorRevalorizacion;
    }
    
    //visibilidad cambiada
    public Jugador getPropietario(){
        return propietario;
    }
    
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        if ( !hipotecado && esEsteElPropietario(jugador)){
            propietario.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        return salida;
    }
    
    private boolean propietarioEncarcelado(){
        return propietario.isEncarcelado();
    }
    
    boolean tienePropietario(){
        boolean comprobacion = true;
        if (propietario == null){
            comprobacion = false;
        }
        return comprobacion;
    }
    
    @Override
    public String toString(){
        String salida;
        String nombreprop;
        
        if (propietario == null){
            nombreprop = "No tiene";
        }
        else{
            nombreprop = propietario.getNombre();
        }
        
        salida = "Precio de compra: " + precioCompra + " | Propietario: " + nombreprop + " | Alquiler Base: " + alquilerBase +
                 " | Número de casas: " + numCasas + " | Número de hoteles: " + numHoteles + " | ";
        return salida;
        
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(!esEsteElPropietario(jugador) && tienePropietario()){
            float precio = getPrecioAlquiler();
            jugador.pagaAlquiler(precio);
            propietario.recibe(precio);
        }
        
    }
    
    boolean vender(Jugador jugador){
        boolean comprobacion = true;
        if(!hipotecado && esEsteElPropietario(jugador)){
            propietario.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
        }
        else
            comprobacion = false;
        
        return comprobacion;
    }
    
    
    
}
