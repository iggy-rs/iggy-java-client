package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.Unpooled;
import rs.iggy.clients.blocking.UsersClient;
import rs.iggy.identifier.UserId;
import rs.iggy.user.*;
import java.util.List;
import java.util.Optional;

class UsersTcpClient implements UsersClient {

    private static final int LOGIN_USER_CODE = 38;
    private final TcpConnectionHandler connection;

    UsersTcpClient(TcpConnectionHandler connection) {
        this.connection = connection;
    }

    @Override
    public UserInfoDetails getUser(UserId userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserInfo> getUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createUser(String username, String password, UserStatus status, Optional<Permissions> permissions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(UserId userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(UserId userId, Optional<String> username, Optional<UserStatus> status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePermissions(UserId userId, Optional<Permissions> permissions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(UserId userId, String currentPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IdentityInfo login(String username, String password) {
        String version = "0.0.1"; //TODO(mm): 5.10.2024 replace version and context
        String context = "java-sdk";
        var payloadSize = 2 + username.length() + password.length() + 4 + version.length() + 4 + context.length();
        var payload = Unpooled.buffer(payloadSize);

        payload.writeByte(username.length());
        payload.writeBytes(username.getBytes());
        payload.writeByte(password.length());
        payload.writeBytes(password.getBytes());
        payload.writeIntLE(version.length());
        payload.writeBytes(version.getBytes());
        payload.writeIntLE(context.length());
        payload.writeBytes(context.getBytes());

        var response = connection.send(LOGIN_USER_CODE, payload);

        var userId = response.readUnsignedIntLE();
        return new IdentityInfo(userId, Optional.empty());
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException();
    }
}
