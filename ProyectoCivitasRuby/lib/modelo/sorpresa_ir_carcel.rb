# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class SorpresaIrCarcel < Sorpresa
    public_class_method :new
    def initialize(tablero)
      super("Ir a la carcel")
      @tablero = tablero
    end
    
    #@Override
    def aplicarajugador(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.numcasillacarcel())
      end
    end
    
    #@Override
    def to_s()
      salida = "#{@texto}\nSorpresa para ir a la carcel"
    end
  end
end