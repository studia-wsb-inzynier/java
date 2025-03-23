package pl.maropce.etutor.domain.user_details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDetailsRepository appUserDetailsRepository;

    public AppUserDetailsServiceImpl(AppUserDetailsRepository appUserDetailsRepository) {
        this.appUserDetailsRepository = appUserDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUserDetails> userDetails = appUserDetailsRepository.findByUsername(username);
        if (userDetails.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return userDetails.get();
    }
}
