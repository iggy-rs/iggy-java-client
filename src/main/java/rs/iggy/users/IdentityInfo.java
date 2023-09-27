package rs.iggy.users;

import java.util.Optional;

public record IdentityInfo(Long userId, Optional<String> token) {
}
