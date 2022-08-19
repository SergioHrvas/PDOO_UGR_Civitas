require_relative '../modelo/civitas_juego'
require_relative '../modelo/salidas_carcel'
require_relative '../modelo/operacion_inmobiliaria'

module Civitas
  class Controlador
    
    #Constructor
    def initialize(juego, vista)
      #Atributos de instancia
      @juego = juego
      @vista = vista  
    end
      
    #Métodos públicos    
    def juega
      @vista.set_civitas_juego(@juego)
        
      while(!@juego.final_del_juego) #mientras no acabe el juego, jugará
        @vista.actualizar_vista()
        @vista.pausa()
        operacion = @juego.siguiente_paso()
        @vista.mostrar_siguiente_operacion(operacion)
          
        if(operacion != Operaciones_juego::PASAR_TURNO)
          @vista.mostrar_eventos()
        end
        final = @juego.final_del_juego()
        if(final == false) #si no ha acabado el juego
          if(operacion == Operaciones_juego::COMPRAR)
            respuesta = @vista.comprar
            if(respuesta == Respuestas::SI)
              @juego.comprar()
            end
            @juego.siguiente_paso_completado(operacion)
            
          elsif (operacion == Operaciones_juego::GESTIONAR)
            @vista.gestionar()
            gestion = @vista.get_gestion()
            propiedad = @vista.get_propiedad()
            lista_respuestas = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias::HIPOTECAR, GestionesInmobiliarias::CANCELAR_HIPOTECA, 
              GestionesInmobiliarias::CONSTRUIR_CASA, GestionesInmobiliarias::CONSTRUIR_HOTEL,GestionesInmobiliarias::TERMINAR]
            operinmo = OperacionInmobiliaria.new(lista_respuestas[gestion], propiedad)
              
            case operinmo.gestion
              when GestionesInmobiliarias::VENDER
                @juego.vender(propiedad)

              when GestionesInmobiliarias::HIPOTECAR
                @juego.hipotecar(propiedad)

              when GestionesInmobiliarias::CANCELAR_HIPOTECA
                @juego.cancelar_hipoteca(propiedad)

              when GestionesInmobiliarias::CONSTRUIR_CASA
                @juego.construir_casa(propiedad)

              when GestionesInmobiliarias::CONSTRUIR_HOTEL
                @juego.construir_hotel(propiedad)

              when GestionesInmobiliarias::TERMINAR
                @juego.siguiente_paso_completado(operacion)
            end
              
          elsif(operacion == Operaciones_juego::SALIR_CARCEL)
            salida = @vista.salir_carcel()
            if(salida == Salidas_carcel::TIRANDO)
              @juego.salir_carcel_tirando()
            elsif(salida == Salidas_carcel::PAGANDO)
              @juego.salir_carcel_pagando()
            end
            @juego.siguiente_paso_completado(operacion)
          end
            
        else  #si es el fin del juego
           
          #Mostramos ranking
          top = Array.new();
          top = @juego.ranking
          for i in 0...top.size()
            puts "#{i+1}.  #{top.at(i).nombre}"
          end    
        end    
      end
    end
    
  end
end
