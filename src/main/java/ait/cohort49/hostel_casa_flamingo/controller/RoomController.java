package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/rooms")
@Tag(name = "Room", description = "Controller for operations with rooms")

public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @Operation(summary = "Get all rooms", description = "Retrieve a list of all rooms",
            security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoomDto.class))),
                            @Content(mediaType = "application/xml",
                                    array = @ArraySchema(schema = @Schema(implementation = RoomDto.class)))
                    }
            )
    })

    /**
     * Получить список всех комнат (GET /rooms).
     */
    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }


    @Operation(summary = "Get room by id", description = "Retrieve room details by its ID",
            security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = RoomDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Room not found"))
            )
    })
    /**
     * Найти комнату по ID (GET /rooms/{id}).
     */
    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }


    @Operation(summary = "Create room", description = "Add a new room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = RoomDto.class))
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
     * Создать новую комнату (POST/rooms).
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDto createRoom(@RequestBody CreateOrUpdateRoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }

    @Operation(summary = "Update room", description = "Update room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room updated",
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
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class,
                                    example = "Room not found"))
            )
    })

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoomDto updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        return roomService.updateRoom(id, roomDto);
    }


    @Operation(summary = "Delete room", description = "Delete room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room deleted",
                    content = @Content
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "403", description = "User doesn't have rights",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User doesn't has rights"))
            ),
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Room not found"))
            )
    })
    /**
     * Удалить комнату (DELETE /rooms/{id}).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }


    @Operation(summary = "Get total bed price", description = "Retrieve the total price of beds in the specified room",
            security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(type = "number", example = "120.99")),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(type = "number", example = "120.99"))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Room not found"))
            )
    })
    /**
     * Получить общую стоимость кроватей по комнате (GET /rooms/{id}/total_price).
     */
    @GetMapping("/{id}/total_price")
    public BigDecimal getTotalBedPrice(@PathVariable Long id) {
        return roomService.getTotalBedPriceForRoom(id);
    }
}
