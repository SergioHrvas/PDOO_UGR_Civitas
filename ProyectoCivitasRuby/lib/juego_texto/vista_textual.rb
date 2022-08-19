#encoding:utf-8
require_relative '../modelo/operaciones_juego'
require 'io/console'
require_relative '../modelo/respuestas'
require_relative '../modelo/gestiones_inmobiliarias'

module Civitas
  class Vista_textual

    def mostrar_estado(estado)
      puts estado
    end
    
    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    
    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end


    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
        "\n"+tab+"Elige una opción: ",
        tab+"Valor erróneo")
      return opcion
    end

    
    def comprar
      opciones = Array.new
      opciones = ["NO","SI"]
      opcion = menu("¿Desea comprar la calle en la que ha caido?", opciones)
      lista_respuestas = Array.new
      lista_respuestas = [Respuestas::NO, Respuestas::SI]
      lista_respuestas[opcion]
    end

    
    def gestionar
      opciones = Array.new
      opciones = ["VENDER", "HIPOTECAR", "CANCELAR_HIPOTECA", "CONSTRUIR_CASA", "CONSTRUIR_HOTEL", "TERMINAR"]
      lista_gestiones_inmobiliarias = Array.new
      lista_gestiones_inmobiliarias = [GestionesInmobiliarias::VENDER,GestionesInmobiliarias::HIPOTECAR, GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, GestionesInmobiliarias::CONSTRUIR_HOTEL, GestionesInmobiliarias::TERMINAR]
      opcion = menu("¿Qué desea realizar?", opciones)
      @igestion = opcion
      if(opcion != 5)
        propiedades = @juegomodel.get_jugador_actual.propiedades
        nombres_propiedades = Array.new
        for propiedad in propiedades
          nombres_propiedades.push(propiedad.nombre)
        end
        opcion = menu("¿Sobre que propiedad desea hacer dicha gestión", nombres_propiedades)
        @ipropiedad = opcion
      end
    end

    
    def get_gestion
      return @igestion
    end

    
    def get_propiedad
      return @ipropiedad
    end

    
    def mostrar_siguiente_operacion(operacion)
      puts operacion
    end
    

    def mostrar_eventos
      while(Diario.instance.eventos_pendientes)
        puts Diario.instance.leer_evento
      end
    end

    
    def set_civitas_juego(civitas)
      @juegomodel=civitas
      actualizar_vista()
    end

    
    def actualizar_vista
      jugador = @juegomodel.get_jugador_actual
      casilla = @juegomodel.get_casilla_actual
      
      puts jugador.to_s
      puts casilla.to_s
    end

    
    def salir_carcel
      opciones = Array.new
      opciones = ["PAGANDO", "TIRANDO"]
      opcion = menu("¿Desea salir de la carcel pagando o tirando?",opciones)
      lista_opciones_salidas = [Salidas_carcel::PAGANDO, Salidas_carcel::TIRANDO]    
      lista_opciones_salidas[opcion];
    end
    
  end
end
