package rs.iggy.user;

import java.util.Optional;

public record IdentityInfo(Long userId, Optional<IdentityTokens> tokens) {
}
