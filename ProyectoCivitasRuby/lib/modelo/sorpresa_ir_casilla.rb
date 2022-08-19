# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class SorpresaIrCasilla < Sorpresa
    public_class_method :new   
    def initialize(tablero, valor, texto)
      super(texto)
      @tablero = tablero
      @valor = valor
    end
    
    #@Override
    def aplicarajugador(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        numero = todos[actual].num_casilla_actual()   
        tirada = @tablero.calcular_tirada(numero, @valor)
        posicion = @tablero.nueva_posicion(numero, tirada)
        todos[actual].mover_a_casilla(posicion)
        @tablero.get_casilla(posicion).recibe_jugador(actual, todos)      
      end
    end
    
    #@Override
    def to_s
      salida = "{@texto}\nSorpresa Ir A Casilla #{@valor}"
    end
    
  end

end
