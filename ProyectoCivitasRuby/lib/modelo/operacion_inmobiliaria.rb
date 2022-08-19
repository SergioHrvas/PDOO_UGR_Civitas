# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class OperacionInmobiliaria
    def initialize(gestion, nro_propiedad)
    @gestion = gestion
    @nro_propiedad = nro_propiedad
    end
  
  attr_reader:gestion, :nro_propiedad
  end
end
