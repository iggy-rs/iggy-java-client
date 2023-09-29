package rs.iggy.user;

import java.util.HashMap;

public record Permissions(
        GlobalPermissions global,
        HashMap<Long, StreamPermissions> streams
) {
}
