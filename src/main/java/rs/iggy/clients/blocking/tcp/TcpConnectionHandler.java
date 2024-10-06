package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

final class TcpConnectionHandler {

    private static final int REQUEST_INITIAL_BYTES_LENGTH = 4;
    private static final int COMMAND_LENGTH = 4;
    private static final int RESPONSE_INITIAL_BYTES_LENGTH = 8;

    private final Connection connection;
    private final Queue<byte[]> responses = new ConcurrentLinkedQueue<>();
    private final Semaphore semaphore = new Semaphore(0);

    TcpConnectionHandler(String host, Integer port) {
        this.connection = TcpClient.create().host(host).port(port).connectNow();
        this.connection.inbound().receive().asByteArray().subscribe(response -> {
            responses.add(response);
            semaphore.release();
        });
    }

    ByteBuf send(int command) {
        return send(command, Unpooled.EMPTY_BUFFER);
    }

    ByteBuf send(int command, ByteBuf payload) {
        var payloadSize = payload.readableBytes() + COMMAND_LENGTH;
        var buffer = Unpooled.buffer(REQUEST_INITIAL_BYTES_LENGTH + payloadSize);
        buffer.writeIntLE(payloadSize);
        buffer.writeIntLE(command);
        buffer.writeBytes(payload);

        connection.outbound().send(Mono.just(buffer)).then().block();
        try { //TODO(mm): 6.10.2024 validate approach and error handling
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var response = responses.poll();
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

    ByteBuf handleResponse(long status, int responseLength, ByteBuf responseBuffer) {
        if (status != 0) {
            throw new RuntimeException("Received an invalid response with status " + status);
        }
        if (responseLength == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        return responseBuffer.readBytes(responseLength);
    }

}
