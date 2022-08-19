# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaPorCasaHotel < Sorpresa
    public_class_method :new 
    def initialize(valor, texto)
      super(texto)
      @valor = valor  
    end
  
    #@Override
    def aplicarajugador(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        todos[actual].modificar_saldo(@valor*todos[actual].cantidad_casas_hoteles)     
      end
    end
    
    def to_s
      salida = "{@texto}\nSorpresa #{@valor} por casa/hotel"
    end
  end
end