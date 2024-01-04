package rs.iggy.clients.blocking;

import rs.iggy.identifier.UserId;
import rs.iggy.user.*;
import java.util.List;
import java.util.Optional;

public interface UsersClient {

    UserInfoDetails getUser(Long userId);

    UserInfoDetails getUser(UserId userId);

    List<UserInfo> getUsers();

    void createUser(String username, String password, UserStatus status, Optional<Permissions> permissions);

    void deleteUser(Long userId);

    void deleteUser(UserId userId);

    void updateUser(Long userId, Optional<String> username, Optional<UserStatus> status);

    void updateUser(UserId userId, Optional<String> username, Optional<UserStatus> status);

    void updatePermissions(Long userId, Optional<Permissions> permissions);

    void updatePermissions(UserId userId, Optional<Permissions> permissions);

    void changePassword(Long userId, String currentPassword, String newPassword);

    void changePassword(UserId userId, String currentPassword, String newPassword);

    IdentityInfo login(String username, String password);

    void logout();

}
