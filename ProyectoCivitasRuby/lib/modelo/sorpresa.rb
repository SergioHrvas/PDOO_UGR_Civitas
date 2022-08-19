# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Sorpresa
    
    #CONSTRUCTORES
    def initialize (texto)
      @texto =  texto
    end
    
    protected
    def informe(actual, todos)
      Diario.instance.ocurre_evento("Aplicando sorpresa a " + todos[actual].nombre + ".\n" + @texto)
    end
   
    #MÉTODOS PUBLICOS
    public
    
    def jugador_correcto(actual, todos)
      (actual>=0 && actual<todos.size)
    end
        
    private_class_method :new
  end
end
