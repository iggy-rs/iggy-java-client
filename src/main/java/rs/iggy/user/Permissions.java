package rs.iggy.user;

import java.util.Map;
import java.util.Optional;

public record Permissions(
        GlobalPermissions global,
        Optional<Map<Long, StreamPermissions>> streams
) {
}
