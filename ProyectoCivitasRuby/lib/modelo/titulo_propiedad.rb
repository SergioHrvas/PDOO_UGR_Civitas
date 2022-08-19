# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module Civitas
  class TituloPropiedad
  
    #Atributo de clase
    @@factorintereseshipoteca = 1.1;
  
    #Constructor
    def initialize(nom, ab, fr, hb, pc, pe)
      #Atributos de instancia
      @nombre = nom
      @alquilerbase = ab
      @factorrevalorizacion = fr
      @hipotecabase = hb
      @preciocompra = pc
      @precioedificar = pe
      @numcasas = 0
      @numhoteles = 0
      @hipotecado = false
    
      #Atributo de referencia
      @propietario = nil
    end
  
    #Lectores
    attr_reader :hipotecado, :propietario, :precioedificar, :preciocompra, :numhoteles, :numcasas, :nombre

    
    def actualiza_propietario_por_conversion(jugador)
      @propietario = jugador
    end
  
    
    def cancelar_hipoteca(jugador)
      result = false
      if(@hipotecado)
        if(es_este_el_propietario(jugador))
          @propietario.paga(get_importe_cancelar_hipoteca())
          @hipotecado = false
          result = true
        end
      end
      result
    end
    
  
    def cantidad_casas_hoteles
      @numcasas + @numhoteles
    end
  
    
    def comprar(jugador)
      result = false
      if(tiene_propietario == false)
        actualiza_propietario_por_conversion(jugador)
        result = true
        @propietario.paga(@preciocompra)
      end
      result
    end
    
  
    def construir_casa(jugador)
      result = false
      if (es_este_el_propietario(jugador))
        jugador.paga(@precioedificar)
        @numcasas = @numcasas+1
        result = true
      end
      result
    end
    
  
    def construir_hotel(jugador)
      result = false
      if (es_este_el_propietario(jugador))
        jugador.paga(@precioedificar)
        @numhoteles = @numhoteles+1
        result = true
      end
      result
    end
  
  
    def derruir_casas(n, jugador)
      comprobacion = false
      if(es_este_el_propietario(jugador) && @numcasas >= n)
        @numcasas=@numcasas-n
        comprobacion = true
      end
      comprobacion
    end
  
    
    def get_importe_cancelar_hipoteca()
      @@factorintereseshipoteca*get_importe_hipoteca()
    end
  
    
    def hipotecar(jugador)
      salida = false
      if (!@hipotecado && es_este_el_propietario(jugador))
        @propietario.recibe(get_importe_hipoteca)
        @hipotecado = true
        salida = true
      end
      salida
    end
  
    
    def tiene_propietario
      @propietario!=nil
    end
 
    
    def tramitar_alquiler(jugador)
      if(tiene_propietario && !es_este_el_propietario(jugador))
        precio = get_precio_alquiler()
        jugador.paga_alquiler(precio)
        @propietario.recibe(precio);
      end
    end
  
    
    def vender(jugador)
      comprobacion = false
      if(es_este_el_propietario(jugador) && !hipotecado)
        cantidad = get_precio_venta
        @propietario.recibe(cantidad)
        actualiza_propietario_por_conversion(nil)
        @numcasas = 0
        @numhoteles = 0
        comprobacion = true
      end
      comprobacion
    end
  

    def to_s
      if (@propietario == nil)
        nombreprop = "no tiene"
      else
        nombreprop = @propietario.nombre
      end
      salida = "Alquiler: #{@alquilerbase} | Propietario: #{nombreprop} | Precio: #{@preciocompra} | Numero de casas: #{@numcasas} | Numero de hoteles: #{@numhoteles}"
    end

    
    
    private
    
    def propietario_encarcelado
      comprobacion = false
      if (@propietario.isencarcelado && tiene_propietario)
        comprobacion = true
      end
      comprobacion
    end
  

    def get_precio_venta
      @preciocompra+(@numcasas+5*@numhoteles)*@precioedificar*@factorrevalorizacion
    end
  
    
    def get_precio_alquiler
      if(@hipotecado || propietario_encarcelado)
        cantidad = 0
      else
        cantidad = @alquilerbase*(1+(@numcasas*0.5)+(@numhoteles*2.5))
      end
      cantidad
    end
  
    
    def es_este_el_propietario(jugador)
      @propietario==jugador
    end

    
    def get_importe_hipoteca
      @hipotecabase*(1+(@numcasas*0.5)+(@numhoteles*2.5))
    end
    
  end
end
