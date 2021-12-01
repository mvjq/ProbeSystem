package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import com.nasa.probesystem.domain.model.dto.ProbeSystemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;

interface ProbeNavigationApi {

    @Operation(
            summary = "Create new probe navigation",
            description = "Created a new navigation plan to the probe on a planet using commands.")
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
  ResponseEntity<ProbeSystemResponse> createProbeNavigation(ProbeSystemRequest request);

    @Operation(
            summary = "Get a probe",
            description = "Get a probe by it id.")
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
  ResponseEntity<ProbeSystemResponse> getProbe(int probeId);

    @Operation(
            summary = "Get all probe",
            description = "Get all probes in the database.")
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
    ResponseEntity<List<ProbeSystemResponse>> getAllProbes();

    @Operation(
            summary = "Updated a probe navigation system.",
            description = "Updated a probe navigation system with new commands")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
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
  ResponseEntity<ProbeSystemResponse> updatedProbeNavigation(
      int probeId, ProbeSystemRequest request);


    @Operation(
            summary = "Delete a probe",
            description = "Delete a probe by its id.")
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
  ResponseEntity<ProbeSystemResponse> deleteProbe(int probeId);
}
