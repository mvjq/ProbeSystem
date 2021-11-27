package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;

public interface Navigation {

  /*
  Esse cara fica responsável por receber um planeta
  Um probe, um planeta e uma lista de comandos
  - Primeiro ele vai pousar no planeta (chamo validações)
  - Dado que ele pousou e tá tudo ok, o land vai voltar um planta que contém a sonda!
  - depois eu vou pegar a lista de comandos e fazer o turn e planet
   */
  Planet navigate(Planet planet, Probe probe, List<Command> command);

  /*
  preciso implementar um métod que vai receber um Planeta, uma sonda e vai 'pousar' ela no planeta
  assim eu consigo associar planeta + sonda e já fazer as validações
  */
  Planet land(Planet planet, Probe probe);

  /*
  Ações que eu vou ter
  Uma sonda virar para L

  uma sonda virar par R
  (esses dosi casos é a mesma função e com o enum fica mais fácil de complementar isso)
    */

  Probe turn(Probe probe, Command command);

  /*
  uma sonda move para frente
   */
  Probe moveForward(Planet planet, Probe probe);
}
