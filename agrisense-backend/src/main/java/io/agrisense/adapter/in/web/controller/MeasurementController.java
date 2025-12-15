package io.agrisense.adapter.in.web.controller;

import io.agrisense.adapter.in.web.dto.CreateMeasurementRequest;
import io.agrisense.ports.in.IProcessMeasurementUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/measurements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeasurementController {

    private final IProcessMeasurementUseCase processMeasurementUseCase;

    @Inject
    public MeasurementController(IProcessMeasurementUseCase processMeasurementUseCase) {
        this.processMeasurementUseCase = processMeasurementUseCase;
    }

    @POST
    public Response postMeasurement(@Valid CreateMeasurementRequest req) {
        // Bean Validation (@NotNull, @Positive) handles field validation
        // GlobalExceptionHandler catches ConstraintViolationException → 400 Bad Request
        // GlobalExceptionHandler catches IllegalArgumentException → 404 Not Found
        
        // Use Case Call (Hexagonal Architecture Pattern)
        processMeasurementUseCase.processMeasurement(
            req.getSensorId(), 
            req.getValue(), 
            req.getUnit() == null ? "" : req.getUnit()
        );

        // 202 Accepted - Processing initiated asynchronously
        return Response.accepted()
                .entity("{\"status\": \"Measurement processed successfully\"}")
                .build();
    }
}