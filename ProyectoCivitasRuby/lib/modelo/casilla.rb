

require_relative "titulo_propiedad"
require_relative "sorpresa"
require_relative "jugador"
require_relative "diario"
require_relative "mazo_sorpresas"

module Civitas  
  class Casilla
    
    #Constructores
    def initialize(nombre)
      @nombre = nombre
    end
  
    
    #MÉTODOS PÚBLICOS
    public    
    
    #Lectores
    attr_reader :nombre
   

    def jugador_correcto(actual, todos)
      (actual < todos.size && actual >= 0)
    end
    
    
    def recibe_jugador(iactual, todos)
        informe(iactual, todos)
    end
        
        
    def to_s
      salida = "Nombre: #{@nombre} Tipo: #{@tipo}"
      salida
    end
    
    
    #MÉTODOS PROTEGIDOS
    protected
       
    def informe(actual, todos)
      Diario.instance.ocurre_evento("El jugador " + "#{todos[actual].nombre} ha caido en la casilla #{@nombre}" + "\n\n")
    end
   
  end
end