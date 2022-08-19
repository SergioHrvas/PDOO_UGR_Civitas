#encoding: UTF-8
require_relative 'dado'
require_relative 'estados_juego'
require_relative 'gestor_estados'
require_relative 'jugador'
require_relative 'mazo_sorpresas'
require_relative 'tablero'
require_relative 'sorpresa'
require_relative 'casilla_calle'
require_relative 'casilla_sorpresa'
require_relative 'casilla_impuesto'
require_relative 'casilla_juez'
require_relative 'casilla'
require_relative 'sorpresa_salir_carcel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_ir_casilla'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_especulador'
module Civitas
  class CivitasJuego
    
    #Atributo de clase - creado para evitar números mágicos en la creación del tablero
    @@casillacarcel=5
    
    #Constructures
    def initialize(nombres)
      @jugadores = Array.new
      for nombre in nombres
        @jugadores.push(Jugador.new(nombre))
      end
      
      @gestorestados = Gestor_estados.new()
      @estado = @gestorestados.estado_inicial
      @indicejugadoractual = Dado.instance.quien_empieza(@jugadores.size)
      @mazo = MazoSorpresas.new(false)
      
      inicializar_tablero(@mazo)
      inicializar_mazo_sorpresas(@tablero)
    end
    
    
    def inicializar_tablero(mazo)
      @tablero = Tablero.new(@@casillacarcel)
      
      #Ya está agregada la Salida en la posición 0 //nom, alquilerb, factorevaloriz, hipotebase, preccompra, precioedificar
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Gonzalo Gallas", 75, 1.05, 400, 500, 350 )))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Severo Ochoa", 90, 1.1, 448, 560, 450 )))
      @tablero.aniade_casilla(CasillaSorpresa.new(mazo, "Sorpresa 1"))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Elvira", 105, 1.1, 504, 630, 550 )))
      #Aquí se añade la Cárcel      
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Méndez Nuñez", 120, 1.2, 568, 710, 650)))
      @tablero.aniade_casilla(CasillaSorpresa.new(mazo, "Sorpresa 2"))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Camino de Ronda", 135, 1.3, 640, 800, 750)))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Acera del Darro", 150, 1.3, 720, 900, 850)))
      @tablero.aniade_casilla(Casilla.new("Parking"))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Gran Vía de Colón", 165, 1.4, 808, 1010, 950)))
      @tablero.aniade_casilla(CasillaImpuesto.new(250,"Impuesto"))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Ángel Ganivet", 180, 1.5, 904, 1130, 1050)))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Reyes Católicos", 195, 1.6, 1008, 1260, 1150)))
      @tablero.aniade_juez()
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Avenida de la Constitución", 210, 1.7, 1120, 1400, 1250)))
      @tablero.aniade_casilla(CasillaSorpresa.new(mazo, "Sorpresa 3"))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Calle Recogidas", 225, 1.8, 1240, 1550, 1350 )))
      @tablero.aniade_casilla(CasillaCalle.new(TituloPropiedad.new("Paseo de los Tristes", 250, 2.0, 1368, 1710, 1450 )))
    end
    
    
    def inicializar_mazo_sorpresas(tablero)
     #@mazo.al_mazo(SorpresaSalirCarcel.new(@mazo))
    # @mazo.al_mazo(SorpresaIrCasilla.new(tablero, 12, "Debes pagar tus impuestos. Dirígete a la casilla 12."))
     @mazo.al_mazo(SorpresaIrCarcel.new(tablero))
    # @mazo.al_mazo(SorpresaIrCasilla.new(tablero, @@casillacarcel,"Dirígete a la carcel de visita"))
    # @mazo.al_mazo(SorpresaIrCasilla.new( tablero, 18, "Están preguntando por ti en las oficinas de Recogidas. Dirigete a la casilla 18"))
    # @mazo.al_mazo(SorpresaPagarCobrar.new( 500, "Has ganado la lotería. Recibes 500"))
    # @mazo.al_mazo(SorpresaPagarCobrar.new( -500, "Debes devolver 500 a hacienda."))
    # @mazo.al_mazo(SorpresaPorCasaHotel.new( 25, "Tus bienes se han revalorizado. Recibes 25 por cada construcción."))
   #  @mazo.al_mazo(SorpresaPorCasaHotel.new( -25, "Debes pagar 25 por cada construcción debido a reparaciones y obras"))
   #  @mazo.al_mazo(SorpresaPorJugador.new( 100, "Es tu cumpleaños, recibes 100 de cada jugador"))
   #  @mazo.al_mazo(SorpresaPorJugador.new( -60, "Tienes deudas pendientes. Das 60 a cada jugador"))
     @mazo.al_mazo(SorpresaEspeculador.new( 500 ))
    end
    
    
    def contabilizar_pasos_por_salida(jugadoractual)
      while (@tablero.porsalida > 0 )
        jugadoractual.pasa_por_salida
      end
    end
    
    
    def pasar_turno
      @indicejugadoractual = (@indicejugadoractual + 1) % @jugadores.size
    end
    
    
    def siguiente_paso_completado(operacion)
      @estado = @gestorestados.siguiente_estado(get_jugador_actual, @estado, operacion)
      
    end
    
    
    def cancelar_hipoteca(ip)
      get_jugador_actual.cancelar_hipoteca(ip)
    end
    
    
    def construir_casa(ip)
      get_jugador_actual.construir_casa(ip)
    end
    
    
    def construir_hotel(ip)
      get_jugador_actual.construir_hotel(ip)
    end
    
    
    def final_del_juego
      comprobacion = false
      for jugador in @jugadores
        if (jugador.en_bancarrota())
          comprobacion = true
        end
      end
      comprobacion
    end
    
    
    def get_casilla_actual
      @tablero.get_casilla(get_jugador_actual.num_casilla_actual)
    end
    
    
    def get_jugador_actual
      @jugadores[@indicejugadoractual]
    end
    
    
    def hipotecar(ip)
      get_jugador_actual.hipotecar(ip)
    end
    
    
    def infojugadortexto
      return get_jugador_actual.to_s
    end
    
    
    def ranking
      @jugadores.sort.reverse
    end
    
    
    def salir_carcel_pagando()
      get_jugador_actual.salir_carcel_pagando
    end
    
    
    def salir_carcel_tirando()
      get_jugador_actual.salir_carcel_tirando
    end
    
    
    def vender(ip)
      get_jugador_actual.vender(ip)
    end
    
    
    def comprar
      jugadoractual = get_jugador_actual
      casilla = get_casilla_actual##
      titulo = casilla.titulopropiedad()
      res = jugadoractual.comprar(titulo)
    end
    
    
    def siguiente_paso()
      jugadoractual = get_jugador_actual()
      operacion = @gestorestados.operaciones_permitidas(jugadoractual, @estado)
      if(operacion == Operaciones_juego::PASAR_TURNO)
        pasar_turno()
        siguiente_paso_completado(operacion)
      elsif(operacion == Operaciones_juego::AVANZAR)
        avanza_jugador()
        siguiente_paso_completado(operacion)
      end
      operacion
    end
    
    
    def avanza_jugador
      jugadoractual = get_jugador_actual()
      posicionactual = jugadoractual.num_casilla_actual()
      tirada = Dado.instance.tirar()
      posicionnueva = @tablero.nueva_posicion(posicionactual, tirada)
      casilla = @tablero.get_casilla(posicionnueva)
      contabilizar_pasos_por_salida(jugadoractual)
      jugadoractual.mover_a_casilla(posicionnueva)
      casilla.recibe_jugador(@indicejugadoractual, @jugadores)
      contabilizar_pasos_por_salida(jugadoractual)
    end
    
  end
end