#Sergio Hervás Cobo
#Javier Suárez Llorens
# Programación y Diseño Orientado a Objetos
# 2 Ingeniería Informática
# Practica 3 Civitas
# Noviembre 2020 

require_relative 'vista_textual'
require_relative '../modelo/civitas_juego'
require_relative 'controlador'

module Civitas
  module JuegoTexto
    
    #Creo la vista por pantalla
    vista = Vista_textual.new()

    #Creo los jugadores
    nombres = Array.new()
    nombres << "Alan Turing"
    nombres << "Ada Lovelace"
    #nombres << "Evelyn Berezin"
   # nombres << "Dennis Ritchie"
    
    #Creo el juego con los jugadores
    juego = CivitasJuego.new(nombres)
    
    #Establezco el modo debug del dado
    Dado.instance.setdebug(true)
      
    #Creo el controlador (para poder jugar)
    controlador = Controlador.new(juego, vista)
    controlador.juega()
    
    puts "===================| FIN DEL JUEGO |==================="
  end
end
