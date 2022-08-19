# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'jugador_especulador'
require_relative 'sorpresa'
module Civitas
  class SorpresaEspeculador < Sorpresa
    
    public_class_method :new 
    
    def initialize(fianza)
      super("Convertir jugador a especulador")
      @fianza = fianza
    end
    
    #@Override
    def aplicarajugador(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        jugador = JugadorEspeculador.nuevo_especulador(todos[actual],@fianza)
        todos[actual] = jugador;
      end
    end
    
    #@Override
    def to_s
            salida = "{@texto}\n"
    end
  end
end