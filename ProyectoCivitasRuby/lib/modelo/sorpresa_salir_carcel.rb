# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaSalirCarcel < Sorpresa
    public_class_method :new 
    def initialize(mazo)
      super("Salir de la carcel")
      @mazo = mazo
    end 
    
    #@Override
    def aplicarajugador(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        comprobacion = false
        for k in todos do
          if (k.tiene_salvoconducto)
            comprobacion = true
          end
        end
          
        if(!comprobacion)
          todos[actual].obtener_salvoconducto(self)
          salir_del_mazo
        end
      end
    end
            
    def salir_del_mazo
        @mazo.inhabilitar_carta_especial(self)
    end

    
    def usada
        @mazo.habilitar_carta_especial(self)
    end

    def to_s
      salida = "{@texto}\nSorpresa para salir de la carcel"
    end
     
  end
end