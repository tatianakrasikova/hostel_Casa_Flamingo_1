package ait.cohort49.hostel_casa_flamingo.controller;


import ait.cohort49.hostel_casa_flamingo.model.dto.AvailableBedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/beds")
@Tag(name = "Bed", description = "Controller for operations with beds")

public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @Operation(summary = "Create bed", description = "Add new bed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bed created",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BedDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = BedDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't have rights",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User doesn't have rights"))
            )
    })

    /**
     * POST /beds
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BedDto saveBed(@RequestBody CreateBedDto createBedDto) {
        return bedService.saveBed(createBedDto);
    }

    @Operation(summary = "Get bed by id", description = "Retrieve bed by its ID", security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BedDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = BedDto.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Bed not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class,
                                    example = "Bed not found")))
    })

    /**
     * GET /beds/id
     */
    @GetMapping("/{id}")
    public BedDto getById(@PathVariable("id") Long id) {
        return bedService.getBedById(id);
    }


    @Operation(summary = "Get all beds", description = "Returns a list of all beds", security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of beds",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BedDto.class))),
                            @Content(mediaType = "application/xml",
                                    array = @ArraySchema(schema = @Schema(implementation = BedDto.class)))
                    }
            )
    })
    /**
     * GET /beds
     */
    @GetMapping()
    public List<BedDto> getAll() {
        return bedService.getAllBeds();
    }

    @Operation(summary = "Update bed", description = "Update bed by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bed updated",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't have rights",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User doesn't have rights"))
            ),
            @ApiResponse(responseCode = "404", description = "Bed not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class,
                                    example = "Bed not found"))
            )
    })

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BedDto updateBed(@PathVariable Long id, @RequestBody BedDto bedDto) {
        return bedService.updateBed(id, bedDto);
    }

    @Operation(summary = "Delete bed", description = "Delete bed by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bed deleted",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't have rights",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User doesn't have rights"))
            ),
            @ApiResponse(responseCode = "404", description = "Bed not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class,
                                    example = "Bed not found"))
            )
    })
    /**
     * DELETE  /beds/id
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        bedService.deleteBedById(id);
    }

    @GetMapping("/available-beds/rooms/{roomId}")
    public List<AvailableBedDto> getAvailableBeds(
            @PathVariable Long roomId,

            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @Schema(description = "Entry date", type = "string", format = "date", example = "2025-02-10")
            @RequestParam @FutureOrPresent LocalDate entryDate,

            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @Schema(description = "Departure date", type = "string", format = "date", example = "2025-02-15")
            @RequestParam @FutureOrPresent LocalDate departureDate) {
        return bedService.getAvailableBeds(roomId, entryDate, departureDate);
    }
}
