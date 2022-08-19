#encoding: UTF-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "casilla"
module Civitas
  class Tablero
    @@numcasillas = 20
    
    def initialize(indice)
      if(indice<1)
        indice=1
      end
      @numcasillacarcel = indice
      @casillas = Array.new
      @casillas.push(Casilla.new("Salida"))
      @porsalida = 0
      @tienejuez = false
    end

    private
    
    def correcto()
      comprobacion = false
      if(@casillas.length>@numcasillacarcel && @tienejuez)
        comprobacion = true
      end
      comprobacion
    end
    
    def correcto_dos (num_casilla)
      comprobacion = false
      if(correcto() && num_casilla >= 0 && num_casilla < @casillas.length)
        comprobacion = true
      end
      comprobacion
    end

    public
 
    attr_reader :numcasillacarcel

    
    def porsalida
      if(@porsalida>0)
        @porsalida=@porsalida-1
        return @porsalida+1
      end
      @porsalida
    end

    def aniade_casilla(casilla)
        if(@casillas.size()==@numcasillacarcel)
          @casillas.push(Casilla.new("Cárcel"))
        end
        @casillas.push(casilla)
        if(@casillas.size()==@numcasillacarcel)
          @casillas.push(Casilla.new("Cárcel"))
        end
    end
 
    
    def aniade_juez()
      if(!@tienejuez)
        @casillas.push(CasillaJuez.new(@numcasillacarcel, "Juez"))
        @tienejuez = true
      end
    end

    
    def get_casilla(numCasilla)
      if(correcto_dos(numCasilla))
        return @casillas[numCasilla]      
      else
        puts "mal"
        return nil
      end
    end

    
    def nueva_posicion (actual, tirada)
      if(correcto()==false)
        return -1;
      else
        actual = actual + tirada
        if (actual >= @@numcasillas)
          actual = actual % @@numcasillas
          @porsalida = @porsalida+1
        end
      end
      actual
    end


    def calcular_tirada (origen, destino)
      tirada = destino - origen
      if(tirada < 0)
        tirada = tirada + 20
      end
      return tirada
    end
    
  end
end