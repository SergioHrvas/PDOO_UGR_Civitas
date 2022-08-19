# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaCalle < Casilla
    
    def initialize(titulo)
      super(titulo.nombre)
      @titulopropiedad = titulo;
    end
    
    attr_reader :titulopropiedad;
    
    #@Override
    def recibe_jugador(iactual, todos)
      if(jugador_correcto(iactual,todos))
        informe(iactual, todos)
        jugador=todos.at(iactual)
        if(@titulopropiedad.tiene_propietario()==false)
          jugador.puede_comprar_casilla()
        else
          @titulopropiedad.tramitar_alquiler(jugador)
        end
      end
    end
    
    #@Override    
    def to_s
      salida = "#{@nombre} | #{@titulopropiedad}"
    end
  end
end