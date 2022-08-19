# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class MazoSorpresas
    
    #Constructor
    def initialize(d = false)
      init
      @debug = d
      if(d)
        Diario.instance.ocurre_evento("MazoSorpresas en modo debug")
      end
    end

    
  
    private
    def init
      #Atributos de referencia
      @sorpresas = Array.new   
      @cartasespeciales = Array.new
    
      #Atributos de instancia (algunos)
      @barajada = false
      @usadas = 0
      @ultimasorpresa = nil
    end
    
    
    
    public
    
    attr_reader :ultimasorpresa
    
    
    def al_mazo(s)
      if(@barajada==false)
        @sorpresas.push(s)
      end
    end
    
    
    def siguiente()
      if(@barajada==false || @usadas == @sorpresas.size())
        if(@debug==false)
          @sorpresas.shuffle!()
        end
        @usadas = 0
        @barajada = true
      end
      @usadas=@usadas+1
      @ultimasorpresa = @sorpresas[0]
      @sorpresas.delete_at(0)
      @sorpresas.push(@ultimasorpresa)
      
      return @ultimasorpresa 
    end
  
    
    def inhabilitar_carta_especial(s)
      if(@sorpresas.index!=nil)  
        @sorpresas.delete(s)
        @cartasespeciales.push(s)
        Diario.instance.ocurre_evento("Carta Especial inhabilitada")
      end
    end
      
    
    def habilitar_carta_especial(s)
      if(@cartasespeciales.index(s))
        @cartasespeciales.delete(s)
        @sorpresas.push(s)
        Diario.instance.ocurre_evento("Carta Especial habilitada")
      end
    end
    
  end
end
