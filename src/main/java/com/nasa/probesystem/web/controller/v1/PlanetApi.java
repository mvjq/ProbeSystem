package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import com.nasa.probesystem.domain.model.dto.ProbeSystemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

interface PlanetApi {

  @Operation(
      summary = "Create a new planet ",
      description = "Add a new planet to the database which probes can navigate.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Object created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProbeSystemResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Something bad happens inside the data access service",
            content = @Content(schema = @Schema(hidden = true)))
      })
  ResponseEntity<ProbeSystemResponse> createPlanet(ProbeSystemRequest request);

  @Operation(
      summary = "Get a planet by Id ",
      description = "Returns a planet by Id if it's in the database.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "302",
            description = "Object found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProbeSystemResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Object not found",
            content = @Content(schema = @Schema(hidden = true)))
      })
  ResponseEntity<ProbeSystemResponse> getPlanet(int planetId);

  @Operation(
      summary = "Get all planets",
      description = "Returns all the planets found in the database.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "302",
            description = "Objects found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProbeSystemResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Object not found",
            content = @Content(schema = @Schema(hidden = true)))
      })
  ResponseEntity<List<ProbeSystemResponse>> getAllPlanets();

  @Operation(
      summary = "Updated a planet by its Id",
      description = "Search a planet in the database by id and if found, update it.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProbeSystemResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Object not found",
            content = @Content(schema = @Schema(hidden = true)))
      })
  ResponseEntity<HttpStatus> updatePlanet(int planetId, ProbeSystemRequest request);

  @Operation(
      summary = "Delete a planet",
      description = "Seach a planet in the database by id and if found, delete it")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "302",
            description = "Object found and deleted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProbeSystemResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Object not found",
            content = @Content(schema = @Schema(hidden = true)))
      })
  ResponseEntity<ProbeSystemResponse> deletePlanet(int planetId);
}
