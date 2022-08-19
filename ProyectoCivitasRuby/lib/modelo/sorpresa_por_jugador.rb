# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  class SorpresaPorJugador < Sorpresa
    public_class_method :new 
    
    def initialize(valor, texto)
      super(texto)
      @valor = valor  
    end

    #@Override
    def aplicarajugador(actual, todos)
      if(jugador_correcto(actual, todos))
        informe(actual, todos)
        s1 = SorpresaPagarCobrar.new(-1*@valor, "PAGAR")
        s2 = SorpresaPagarCobrar.new(@valor, "COBRAR")  
        for k in (0..todos.size) do
          if (k != actual)
            s1.aplicarajugador(k, todos)
            k += 1
          end
        end
        s2.aplicarajugador(actual, todos)     
      end
    end
    
    def to_s
      salida = "{@texto}\nSorpresa #{@valor} Por Jugador"
    end
 
  end
end