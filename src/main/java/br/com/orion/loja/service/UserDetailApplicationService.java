package br.com.orion.loja.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.orion.loja.entity.User;
import br.com.orion.loja.repository.UserRepository;

/**
 * UserDetailService
 */
@Service
public class UserDetailApplicationService implements UserDetailsService {

    private final UserRepository userRepository;
    private String strRoles;

    public UserDetailApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByUsernameFetchRole(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        strRoles = "";                
        List<String> rolesNames = user.getRoles().stream().map(u -> u.getName()).collect(Collectors.toList());
        rolesNames.forEach(rn -> strRoles += "ROLE_" + rn + ",");
        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(strRoles);

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
    }
    
}