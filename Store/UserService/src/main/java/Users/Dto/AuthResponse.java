package Users.Dto;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {
}
