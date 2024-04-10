package Users.Services;



import Users.Dto.AuthResponse;
import Users.Dto.UserRequest;
import Users.Dto.UserResponse;
import jakarta.ws.rs.NotFoundException;


import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse getUserById(String id) throws NotFoundException;

    AuthResponse registerUser(UserRequest userRequest) throws Exception;
    AuthResponse authenticate(UserRequest userRequest) throws Exception;

    void deleteById(String id) throws NotFoundException;


}
