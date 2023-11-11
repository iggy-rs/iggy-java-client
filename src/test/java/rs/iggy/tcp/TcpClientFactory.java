package rs.iggy.tcp;

import org.testcontainers.containers.GenericContainer;
import static rs.iggy.IntegrationTest.TCP_PORT;

class TcpClientFactory {

    static IggyTcpClient create(GenericContainer iggyServer) {
        String address = iggyServer.getHost();
        Integer port = iggyServer.getMappedPort(TCP_PORT);
        return new IggyTcpClient(address, port);
    }

}
