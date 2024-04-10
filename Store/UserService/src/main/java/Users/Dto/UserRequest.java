package Users.Dto;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder

public record UserRequest(String login, String password) {
}
