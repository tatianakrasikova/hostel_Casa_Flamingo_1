package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.exception.ExceptionResponseDto;
import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.exception.ValidationErrorResponse;
import ait.cohort49.hostel_casa_flamingo.model.dto.CartDatesDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.security.dto.AuthResponse;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Контроллер для управления операциями, связанными с корзиной пользователя.
 * Этот класс предоставляет конечные точки API для управления содержимым корзины,
 * включая добавление, получение, удаление товаров, расчет общей стоимости и очистку корзины.
 */
@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Controller for operations with cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    /**
     * Получает корзину аутентифицированного пользователя.
     *
     * @param userEmail Email аутентифицированного пользователя, полученный из контекста безопасности.
     * @return Корзина, связанная с пользователем.
     */
    @Operation(summary = "Get user's cart", description = "Retrieve the cart for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = CartDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartDto getCart(@AuthenticationPrincipal
                           @Parameter(hidden = true)
                           String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return cartService.getCart(user);
    }

    /**
     * Добавляет конкретную кровать в корзину аутентифицированного пользователя.
     *
     * @param bedId        Уникальный идентификатор кровати, добавляемой в корзину.
     * @param userEmail    Email аутентифицированного пользователя.
     * @param cartDatesDto Объект с диапазоном дат заезда и выезда.
     * @throws RestException если дата заезда позже даты выезда.
     */
    @Operation(summary = "Add bed to cart", description = "Add a bed to the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bed successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or date range",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bed or cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PostMapping("/bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addBedToCart(@PathVariable
                             @Parameter(example = "1")
                             Long bedId,

                             @AuthenticationPrincipal
                             @Parameter(hidden = true)
                             String userEmail,

                             @Valid
                             @RequestBody
                             CartDatesDto cartDatesDto) {

        LocalDate entryDate = cartDatesDto.getEntryDate();
        LocalDate departureDate = cartDatesDto.getDepartureDate();

        if (entryDate.isAfter(departureDate)) {
            throw new RestException("Entry date cannot be after departure date.");
        }

        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.addBedToCart(user, bedId, entryDate, departureDate);
    }


    /**
     * Удаляет кровать из корзины аутентифицированного пользователя.
     *
     * @param bedId     Уникальный идентификатор кровати, удаляемой из корзины.
     * @param userEmail Email аутентифицированного пользователя.
     */
    @Operation(summary = "Remove bed from cart", description = "Remove a bed from the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bed successfully removed"),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bed or cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @DeleteMapping("/remove_bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBedFromCart(@PathVariable
                                  @Parameter(example = "1")
                                  Long bedId,

                                  @AuthenticationPrincipal
                                  @Parameter(hidden = true)
                                  String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.removeBedFromCart(user, bedId);
    }


    /**
     * Получает общую стоимость всех кроватей в корзине аутентифицированного пользователя.
     *
     * @param userEmail Email аутентифицированного пользователя.
     * @return Общая стоимость товаров в корзине в формате BigDecimal.
     */
    @Operation(summary = "Get total price", description = "Get the total price of all beds in the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(type = "number", example = "120.99"))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/total_price")
    @PreAuthorize("isAuthenticated()")
    public BigDecimal getTotalPrice(@AuthenticationPrincipal
                                    @Parameter(hidden = true)
                                    String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return cartService.getTotalPrice(user);
    }


    /**
     * Очищает корзину аутентифицированного пользователя, удаляя все товары.
     *
     * @param userEmail Email аутентифицированного пользователя.
     */
    @Operation(summary = "Clear the cart", description = "Completely clears the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart successfully cleared"),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @DeleteMapping("/clear")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@AuthenticationPrincipal
                          @Parameter(hidden = true)
                          String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.clearUserCart(user);
    }
}
