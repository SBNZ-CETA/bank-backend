package bank.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bank.repository.UserRepository;
import bank.security.domain.RegisterDto;
import bank.utils.EmailValidator;
import facts.User;
import facts.UserType;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
 
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with username '"+username+"' not found!"));
        return user;
    }

    public boolean register(RegisterDto request) {
        checkUserValidity(request.getUsername(),request.getEmail());
        userRepository.save(makeUserFromRequest(request));
        return true;
    }

    private User makeUserFromRequest(RegisterDto request) {
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        return new User(request.getName(),request.getSurname(),request.getEmail(),request.getUsername(),
                encodedPassword, UserType.CLIENT);
    }

    private void checkUserValidity(String username, String email) {
        if(!emailValidator.test(email)) throw new IllegalStateException("Email is not valid!");
        boolean userExists = userRepository.findUserByUsername(username).isPresent();
        if(userExists) throw new IllegalStateException("User with username '"+username+"' already exists!");
        userExists = userRepository.findUserByEmail(email).isPresent();
        if(userExists) throw new IllegalStateException("User with email '"+email+"' already exists!");
    }

    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

}
