# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaSorpresa < Casilla
    def initialize(mazo, nombre)
      super(nombre)
      @mazo = mazo
      @sorpresa = nil
    end
  
    #@Override
    def recibe_jugador(iactual, todos)
      if(jugador_correcto(iactual, todos))
        @sorpresa = @mazo.siguiente()
        informe(iactual, todos)
        @sorpresa.aplicarajugador(iactual, todos)
      end
    end

    #@Override    
    def to_s
      salida = "Casilla #{@nombre}"
    end
    
  end
end