package rs.iggy.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import rs.iggy.identifier.UserId;
import rs.iggy.user.*;
import java.util.List;
import java.util.Optional;

class UsersTcpClient implements UsersClient {

    private static final int REQUEST_INITIAL_BYTES_LENGTH = 4;
    private static final int LOGIN_USER_CODE = 38;
    private static final int COMMAND_LENGTH = 4;
    private static final int RESPONSE_INITIAL_BYTES_LENGTH = 8;
    private final Connection connection;

    public UsersTcpClient(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserInfoDetails getUser(Long userId) {
        throw new UnsupportedOperationException();
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
    public void deleteUser(Long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(UserId userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(Long userId, Optional<String> username, Optional<UserStatus> status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(UserId userId, Optional<String> username, Optional<UserStatus> status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePermissions(Long userId, Optional<Permissions> permissions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePermissions(UserId userId, Optional<Permissions> permissions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(UserId userId, String currentPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IdentityInfo login(String username, String password) {
        var payloadSize = 2 + username.length() + password.length();
        var payload = Unpooled.buffer(payloadSize);

        payload.writeByte(username.length());
        payload.writeBytes(username.getBytes());
        payload.writeByte(password.length());
        payload.writeBytes(password.getBytes());

        var response = sendWithResponse(LOGIN_USER_CODE, payload);

        var userId = response.readUnsignedIntLE();
        return new IdentityInfo(userId, Optional.empty());
    }

    private ByteBuf sendWithResponse(int command, ByteBuf payload) {
        var payloadSize = payload.readableBytes() + COMMAND_LENGTH;
        var buffer = Unpooled.buffer(REQUEST_INITIAL_BYTES_LENGTH + payloadSize);
        buffer.writeIntLE(payloadSize);
        buffer.writeIntLE(command);
        buffer.writeBytes(payload);

        connection.outbound().send(Mono.just(buffer)).then().block();
        var response = connection.inbound().receive().asByteArray().blockFirst();
        if (response == null) {
            throw new RuntimeException("No response");
        }

        var responseBuffer = Unpooled.wrappedBuffer(response);
        if (!responseBuffer.isReadable(RESPONSE_INITIAL_BYTES_LENGTH)) {
            throw new RuntimeException("Received an invalid or empty response");
        }

        var status = responseBuffer.readUnsignedIntLE();
        var responseLengthL = responseBuffer.readUnsignedIntLE();
        // unsafe cast
        var responseLength = (int) responseLengthL;

        return handleResponse(status, responseLength, responseBuffer);
    }

    private ByteBuf handleResponse(long status, int responseLength, ByteBuf responseBuffer) {
        if (status != 0) {
            throw new RuntimeException("Received an invalid response with status " + status);
        }
        if (responseLength == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return responseBuffer.readBytes(responseLength);
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException();
    }
}
