package facts;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private Long age;
    @Enumerated(EnumType.STRING)
    private UserType type;

    public User(String name, String surname, String email, String username, String password, UserType type, Long age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(type.name());
        return Collections.singletonList(authority);
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;    
    }
    @Override
    public boolean isEnabled() {
        return true;    
    }
}
