#encoding: UTF-8
require_relative "diario"
require_relative "titulo_propiedad"
require_relative "dado"

module Civitas
  class Jugador
    
    #ATRIBUTOS DE INSTANCIA DE CLASE
    @casasmax = 4
    @hotelesmax = 4

    #ATRIBUTOS DE CLASE
    @@pasoporsalida = 1000.0
    @@preciolibertad = 200.0
    @@saldoinicial = 7000.0
    @@casasporhotel = 4
    
    
    #CONSTRUCTORES
    def initialize(nombre)
      #Atributos de instancia
      @nombre = nombre
      @propiedades = Array.new      #excepcion, atributo de referencia
      @salvoconducto = nil
      @encarcelado = false
      @num_casilla_actual = 0
      @puede_comprar = false 
      @saldo = @@saldoinicial
    end
    

    def copia(copia)
      @nombre = copia.nombre;
      @encarcelado = copia.encarcelado
      @num_casilla_actual = copia.num_casilla_actual
      @puede_comprar = copia.puede_comprar
      @saldo = copia.saldo
      @salvoconducto = copia.salvoconducto
      @propiedades = copia.propiedades
    end
    
    protected
    #LECTORES DE ATRIBUTOS DE CLASE
    attr_reader :pasoporsalida, :preciolibertad, :casasporhotel

    #LECTORES DE ATRIBUTOS DE INSTANCIA DE CLASE
    def self.casasmax
      @casasmax
    end
    
    def self.hotelesmax
      @hotelesmax
    end
    #MÉTODOS PÚBLICOS
    public 
    #LECTORES DE ATRIBUTOS DE INSTANCIA
    attr_reader :nombre, :num_casilla_actual, :saldo, :puede_comprar, :encarcelado, :propiedades, :salvoconducto
    


    
    def <=>(otro)
      @saldo <=> otro.saldo
    end
    
    
    def to_s
      salida = "#{@nombre} (#{@saldo}) en la casilla #{@num_casilla_actual}"
    end
   
    
    def isencarcelado()
      @encarcelado
    end
    
    
    def cancelar_hipoteca(ip)
      result = false
      if (@encarcelado == false)
        if(existe_la_propiedad(ip))
          propiedad = @propiedades.at(ip)
          cantidad = propiedad.get_importe_cancelar_hipoteca
          puedo_gastar = puedo_gastar(cantidad)
            
          if(puedo_gastar)
            result = propiedad.cancelar_hipoteca(self)
              
            if (result)
              Diario.instance.ocurre_evento("El jugador #{nombre} cancela la hipoteca de la propiedad #{ip}") 
            end 
          end   
        end      
      end
      result
    end
    
    
    def cantidad_casas_hoteles
      contador = 0
      for i in @propiedades
        contador+=i.cantidad_casas_hoteles();
      end
      contador
    end
    
    
    def comprar(titulo)
      result = false;
      if(@encarcelado == false)
        if(@puede_comprar)
          precio = titulo.preciocompra()
          if(puedo_gastar(precio))
            result = titulo.comprar(self)
            
            if(result)
              @propiedades.push(titulo)
              Diario.instance.ocurre_evento("El jugador #{nombre} compra la propiedad #{titulo.nombre}")
            end
            
            @puede_comprar = false;
            
          end
        end
      end
      result
    end
    
    
    def construir_casa(ip)
      result = false
      if(!@encarcelado)
        existe = existe_la_propiedad(ip)

        if(existe)
          propiedad = @propiedades.at(ip)
          puedo_edificar_casa =  puedo_edificar_casa(propiedad)
          
          if(puedo_edificar_casa)
            result = propiedad.construir_casa(self)
            
            if(result)
              Diario.instance.ocurre_evento("El jugador #{nombre} construye casa en la propiedad #{ip}")
            end
          end
          
        end
      end
      result
    end
    
    
    def construir_hotel(ip)
      result = false
      if (!@encarcelado)        
        if(existe_la_propiedad(ip))
          propiedad = @propiedades.at(ip)
          puedo_edificar_hotel = puedo_edificar_hotel(propiedad)

          
          if(puedo_edificar_hotel)
            result = propiedad.construir_hotel(self)
            propiedad.derruir_casas(@@casasporhotel, self)
            Diario.instance.ocurre_evento("El jugador #{nombre} construye hotel en la propiedad #{ip}")
            
          end
        end     
      end
      result
    end
    
    
    def en_bancarrota()
      comprobacion = false
      if(@saldo <= 0)
        comprobacion = true
      end
      
      comprobacion;
    end
    
    
    def encarcelar(num_casilla_carcel)
      if(debe_ser_encarcelado)
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("El jugador ha sido encarcelado")   
      end
     
      @encarcelado
    end
    
    
    def hipotecar(ip)
      result = false
      if(!@encarcelado)
        if(existe_la_propiedad(ip))
          propiedad = propiedades.at(ip)
          result = propiedad.hipotecar(self)
          
        end
        
        if (result)
          Diario.instance.ocurre_evento("El jugador #{@nombre} hipoteca la propiedad #{ip}")
        end
      end
      result
    end
    
    
    def modificar_saldo(cantidad)
      @saldo = @saldo + cantidad;
      Diario.instance.ocurre_evento("Saldo modificado en #{cantidad}")
      true
    end
    
    
    def mover_a_casilla(numcasilla)
      comprobacion = false
      if (!@encarcelado)
        @num_casilla_actual = numcasilla
        @puede_comprar = false
        Diario.instance.ocurre_evento("El jugador #{nombre} se ha movido a la casilla #{numcasilla}")
        comprobacion = true
      end
    end
    
    
    def obtener_salvoconducto(sorpresa)
      comprobacion = false
      if(@encarcelado == false)
        @salvoconducto = sorpresa
        comprobacion = true
      end
    end
    
    
    def paga(cantidad)
      modificar_saldo(cantidad*-1)
    end
    
    
    def paga_alquiler(cantidad)
      comprobacion = false
      if(!@encarcelado)
        paga(cantidad)
      end
    end
    
    
    def paga_impuesto(cantidad)
      comprobacion = false
      if(!@encarcelado)
        paga(cantidad)
        comprobacion = true
      end
    end
    
    
    def pasa_por_salida
      modificar_saldo(@@pasoporsalida)
      Diario.instance.ocurre_evento("El jugador ha pasado por la salida")
      true
    end
    
    
    def puede_comprar_casilla
      comprobacion = false
      if(!@encarcelado)
        comprobacion = true
      end
      @puede_comprar = comprobacion
    end

    def recibe (cantidad)
      comprobacion = false
      if(!@encarcelado)
        modificar_saldo(cantidad)
      end
    end
    
    
    def salir_carcel_pagando
      comprobacion = false
      if(@encarcelado && puede_salir_carcel_pagando)
        paga(@@preciolibertad)
        @encarcelado = false
        Diario.instance.ocurre_evento("El jugador ha salido de la carcel pagando")
        comprobacion = true
      end
    end
    
    
    def salir_carcel_tirando
      comprobacion = Dado.instance.salgo_de_la_carcel
      if (comprobacion)
        @encarcelado = false
        Diario.instance.ocurre_evento("El jugador ha salido de la carcel tirando")
      end
      comprobacion
    end
    
    
    def tiene_algo_que_gestionar
      @propiedades.size>0
    end
    
    
    def tiene_salvoconducto
      return @salvoconducto!=nil
    end
    
    
    def vender(ip)
      comprobacion = false
      if (!@encarcelado)
        if (existe_la_propiedad(ip))
          vendida = @propiedades.at(ip).vender(self)
          if (vendida)
            Diario.instance.ocurre_evento("La propiedad #{@propiedades.at(ip).nombre} ha sido vendida")
            @propiedades.delete_at(ip)
            comprobacion = true
          end
        end
      end
      comprobacion
    end
    
    
    
    #MÉTODOS PROTEGIDOS
    protected  
    
    
    def debe_ser_encarcelado
      comprobacion = false
      if(!@encarcelado)
        if(!tiene_salvoconducto())
          comprobacion = true
        else
          perder_salvoconducto()
          Diario.instance.ocurre_evento("Salvoconducto usado. El usuario se libra de la cárcel")
        end
      end
      comprobacion
    end
  
    
    
    #MÉTODOS PRIVADOS
    private

    
    def existe_la_propiedad(ip)
      return (ip >= 0 && ip < @propiedades.size())
    end
    
    
    def perder_salvoconducto
      @salvoconducto.usada
      @salvoconducto = nil
    end
    
    
    def puede_salir_carcel_pagando
      return (@saldo >= @@preciolibertad)
    end
    
    
    def puedo_edificar_casa(propiedad)     
      puedo_edificar_casa = false
      precio = propiedad.precioedificar()
      
      if(puedo_gastar(precio) && propiedad.numcasas < Jugador.casasmax)
        puedo_edificar_casa = true
      end
          
      puedo_edificar_casa
    end
   
    
    def puedo_edificar_hotel(propiedad)
      puedo_edificar_hotel = false;
      precio = propiedad.precioedificar()
           
      if(puedo_gastar(precio) && propiedad.numhoteles < Jugador.hotelesmax && propiedad.numcasas >= @@casasporhotel)
        puedo_edificar_hotel = true
      end
      puedo_edificar_hotel
    end
    
    
    def puedo_gastar(precio)
      comprobacion = false
      if(!@encarcelado)
        comprobacion = @saldo>= precio
      end
    end
    

  end
end