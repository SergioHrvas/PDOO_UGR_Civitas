# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaJuez < Casilla
    
    def initialize(numcasillacarcel, nombre)
      super(nombre)
      @carcel = numcasillacarcel    
    end
    
    #@Override
    def recibe_jugador(iactual, todos)
      if(jugador_correcto(iactual, todos))
        informe(iactual, todos)
        todos[iactual].encarcelar(@carcel)
      end
    end
    
    #@Override    
    def to_s
      salida = "Casilla #{@nombre}"
    end
  end
end