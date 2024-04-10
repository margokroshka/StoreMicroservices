package Users.Services;

import Users.Dto.AuthResponse;
import Users.Dto.UserDto;

import Users.Dto.UserRequest;
import Users.Dto.UserResponse;
import Users.Repository.UsersRepository;

import Users.util.JWTService;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UsersRepository usersRepository;
    UserService userService;

    JWTService jwtService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    ModelMapper modelMapper;

    @Override
    public AuthResponse registerUser(UserRequest userRequest) throws Exception {
        if (usersRepository.findByLogin(userRequest.login()).isEmpty()) {
            UserDto userDto = UserDto.builder()
                    .login(userRequest.login())
                    .password(passwordEncoder.encode(userRequest.password()))
                    .build();
            usersRepository.saveAndFlush(userDto);
            String jwtToken = jwtService.generateToken(userDto);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new Exception(
                    String.format("user already exist " +
                            userRequest.login())
            );
        }
    }
    @Override
    public AuthResponse authenticate(UserRequest userRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.login(),
                        userRequest.password()
                )
        );
        UserDto user = usersRepository.findByLogin(userRequest.login())
                .orElseThrow(() -> new Exception(
                                String.format("user not found " +
                                        userRequest.login())
                        )
                );
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public List<UserResponse> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(userDto -> modelMapper.map(userDto, UserResponse.class))
                .toList();
    }

    @Override
    public UserResponse getUserById(String id) throws NotFoundException {
        UserDto userDto = usersRepository.findByLogin(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("Book wth id not found", id)
                ));
        return modelMapper.map(userDto, UserResponse.class);
    }

    @Override
    public void deleteById(String id)  {
        usersRepository.deleteById(id);
    }

}
