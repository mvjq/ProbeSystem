package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.Planet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface NavigationApi {

  @Operation(
      summary = "Add a new planet which a probe can navigate",
      description = "Add a new planet")
  @ApiResponses(
      value = {
        @ApiResponse(
            // TODO: a righ status code here
            responseCode = "200",
            description = "Successful operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  // TODO: add the new request dto type
                  schema = @Schema(implementation = Planet.class))
            }),
        // TODO: add more information about return code after implements the rule
        // @ApiResponse(responseCode = "405", description = "")
      })
  ResponseEntity<Planet> createPlanet(Planet planet);

  ResponseEntity<Planet> getPlanet(String planetName);

  ResponseEntity<List<Planet>> getAllPlanets();

  ResponseEntity<Planet> updatePlanet(String planetName, Planet planet);

  ResponseEntity<Planet> deletePlanet(String planetName);
}
