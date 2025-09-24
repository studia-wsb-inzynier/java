package pl.maropce.etutor.domain.user_details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;
import pl.maropce.etutor.domain.user_details.exception.AccountNotActiveException;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDetailsRepository appUserDetailsRepository;

    public AppUserDetailsServiceImpl(AppUserDetailsRepository appUserDetailsRepository) {
        this.appUserDetailsRepository = appUserDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDetails userDetails = appUserDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!userDetails.isEnabled()) {
            throw new AccountNotActiveException();
        }

        return userDetails;
    }
}
