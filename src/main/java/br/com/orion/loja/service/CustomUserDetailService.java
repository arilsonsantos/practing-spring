package br.com.orion.loja.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.orion.loja.entity.User;
import br.com.orion.loja.repository.UserRepository;

/**
 * CustomUserDetailService
 */
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByUsernameFetchRole(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> rolenames = user.getRoles().stream().map(n -> n.getName()).collect(Collectors.toList());

        String strRoles = "";

        for (int i = 0; i < rolenames.size(); ++i, strRoles += ",") {
            strRoles += "ROLE_" + rolenames.get(i);
        }

        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(strRoles);

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
    }

}