package ru.samoylov.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.samoylov.backend.dto.JwtResponse;
import ru.samoylov.backend.dto.RegistrationRequest;
import ru.samoylov.backend.entity.User;
import ru.samoylov.backend.exception.BaseException;
import ru.samoylov.backend.exception.RegisterException;
import ru.samoylov.backend.exception.UserNotFoundException;
import ru.samoylov.backend.repository.UserRepository;
import ru.samoylov.backend.security.jwt.JwtService;
import ru.samoylov.commondto.EmailTask;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailTaskProducer emailTaskProducer;

    public JwtResponse register(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.info("Email уже существует");
            throw new RegisterException(HttpStatus.CONFLICT, "Email уже используется");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getEmail());
        emailTaskProducer.sendEmailTask(new EmailTask(request.getEmail(),"Спасибо за регистрацию","Привет брух"));
        return new JwtResponse(token);
    }

    public JwtResponse login(RegistrationRequest request) {
      try {
          Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          request.getEmail(),
                          request.getPassword()
                  )
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
          User user = userRepository.findByEmail(request.getEmail())
                  .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

          String token = jwtService.generateToken(user.getEmail());
          log.info("успешная авторизация");
          return new JwtResponse(token);
      }catch (BadCredentialsException e){
          throw  new RegisterException(HttpStatus.FORBIDDEN,"не верный логин или пароль");
      }
    }

    public User getCurrentUserByEmail(UserDetails userDetails) {
        if (userDetails == null) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, "пользователь неавторизован либо токен истек");
        }
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return user;
    }
}
