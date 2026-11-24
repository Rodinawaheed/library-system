

package com.flairs.libraryapi.auth;

import com.flairs.libraryapi.dto.Mapper;
import com.flairs.libraryapi.dto.MemberResponse;
import com.flairs.libraryapi.exception.NotFoundException;
import com.flairs.libraryapi.model.Member;
import com.flairs.libraryapi.repository.MemberRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(15);

    private final MemberRepository members;
    private final PasswordEncoder passwords;
    private final TokenService tokens;

    public AuthService(MemberRepository members, PasswordEncoder passwords, TokenService tokens) {
        this.members = members;
        this.passwords = passwords;
        this.tokens = tokens;
    }

    @Transactional(noRollbackFor = InvalidCredentialsException.class)
    public TokenResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);

        Member member = members.findByEmailIgnoreCase(email)
                .orElseThrow(InvalidCredentialsException::new);

        Instant now = Instant.now();


        if (member.getLockedUntil() != null) {
            if (member.getLockedUntil().isAfter(now)) {
                throw new InvalidCredentialsException();
            }


            member.setFailedLoginAttempts(0);
            member.setLockedUntil(null);
        }


        if (!passwords.matches(request.password(), member.getPasswordHash())) {
            int attempts = member.getFailedLoginAttempts() + 1;
            member.setFailedLoginAttempts(attempts);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                member.setLockedUntil(now.plus(LOCK_DURATION));
                log.warn("Member {} account locked after failed login attempts", member.getId());
            }

            throw new InvalidCredentialsException();
        }


        member.setFailedLoginAttempts(0);
        member.setLockedUntil(null);

        log.info("Member {} logged in successfully", member.getId());

        return tokens.issue(member);
    }

    public MemberResponse me(Long memberId) {
        return Mapper.toResponse(members.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member", memberId)));
    }
}
