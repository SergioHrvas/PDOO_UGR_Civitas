# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "jugador"


module Civitas
  class JugadorEspeculador < Jugador
#    public_class_method :new 

    @@factor_especulador = 2    
    
    def initialize(fianza)     
      @fianza = fianza
    end
    
    def self.nuevo_especulador(jugador, fianza)
        espec = new(fianza)
        espec.copia(jugador)
            
      for propiedad in espec.propiedades
        propiedad.actualiza_propietario_por_conversion(self)
      end
      
      espec
    end
    
    #@Override
    def paga_impuesto(cantidad)
      super(cantidad/2)
    end
    
    
    protected
    #@Override
    def debe_ser_encarcelado
      debeserlo = super
      if(debeserlo && puedo_gastar(@fianza))
        debeserlo = false
        paga(@fianza)
        Diario.instance.ocurre_evento("Fianza pagada. El especulador se libra de la carcel.")
      end
      debeserlo
    end
    
    
    #@Override
    def puedo_edificar_casa(propiedad)
      puedo = false
      precio = propiedad.precioedificar()
      if(puedo_gastar(precio) && propiedad.numcasas < casasmax)
        puedo = true
      end
      puedo
    end
    
    
    #@Override
    def puedo_edificar_hotel(propiedad)
      puedo = false
      precio = propiedad.precioedificar()
      if(puedo_gastar(precio) && propiedad.numhoteles < hotelesmax && propiedad.numcasas >= @@casasporhotel)
        puedo = true
      end
      puedo
    end
    
    #@Override
    def hotelesmax
      Jugador.hotelesmax*@@factor_especulador
    end
    
    
    #@Override
    def casasmax
      Jugador.casasmax*@@factor_especulador
    end
    
    
    #@Override
    public
    def to_s
      salida = "Jugador Especulador #{super.to_s}"
    end

    
  end
end