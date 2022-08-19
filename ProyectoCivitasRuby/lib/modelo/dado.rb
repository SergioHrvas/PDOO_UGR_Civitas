# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require 'singleton'
require_relative 'diario'

module Civitas
  class Dado 
    include Singleton
    
    #Atributo de clase
    @@salidacarcel = 5

    
    #Constructor
    def initialize
      #Atributos de instancia
      @ultimoresultado = 0
      @debug = false
      @random = Random.new
    end

    
    #Métodos públicos
    
    #Lector
    attr_reader :ultimoresultado
    
    
    def tirar()
      if (@debug == false)
        tirada = @random.rand(1..6)
      else
        tirada = 1
      end
      @ultimoresultado = tirada
    end

    
    def salgo_de_la_carcel()
      tirada = tirar()
      comprobacion = false
      if (tirada == @@salidacarcel)        
        comprobacion = true
      end
      comprobacion
    end

    
    def quien_empieza(n)
      jugador = @random.rand(0..n-1)
    end

    
    def setdebug(d)
      @debug = d
      if(@debug)
        cadena = "Modo debug del dado iniciado."
      else
        cadena = "Modo normal del dado iniciado."
      end
      Diario.instance.ocurre_evento(cadena)
    end

  end
end