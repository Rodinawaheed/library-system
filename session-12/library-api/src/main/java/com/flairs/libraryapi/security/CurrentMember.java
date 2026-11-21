
package com.flairs.libraryapi.security;

import com.flairs.libraryapi.model.Role;
import org.springframework.security.oauth2.jwt.Jwt;

public record CurrentMember(Long id, String email, Role role) {

    public static CurrentMember from(Jwt jwt) {
        return new CurrentMember(
                Long.valueOf(jwt.getSubject()),
                jwt.getClaimAsString("email"),
                Role.valueOf(jwt.getClaimAsStringList("roles").getFirst())
        );
    }

    public boolean isLibrarian() {
        return role == Role.LIBRARIAN;
    }

    public boolean canSee(Long memberId) {
        return isLibrarian() || id.equals(memberId);
    }
}
