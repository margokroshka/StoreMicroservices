package Users.Controller;

import Users.Dto.AuthResponse;
import Users.Dto.UserRequest;
import Users.Services.UserService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @Operation(
            summary = "Registration of a new User",
            description = "Registration of a new user with the entered information.",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been successfully registered",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> registrationUser(@RequestBody UserRequest registrationUser) throws Exception {
        return ResponseEntity
                .ok()
                .body(userService.registerUser(registrationUser));
    }

    @Operation(
            summary = "User Authentication",
            description = "Authentication the user with the entered information.",
            tags = {"tutorials", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful authentication",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody UserRequest request) throws Exception {
        return ResponseEntity
                .ok()
                .body(userService.authenticate(request));
    }
}
