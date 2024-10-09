package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.ArrayUtils;
import rs.iggy.identifier.Identifier;
import java.math.BigInteger;

final class BytesSerializer {

    private BytesSerializer() {
    }

    static ByteBuf toBytes(Identifier identifier) {
        if (identifier.getKind() == 1) {
            ByteBuf buffer = Unpooled.buffer(6);
            buffer.writeByte(1);
            buffer.writeByte(4);
            buffer.writeIntLE(identifier.getId().intValue());
            return buffer;
        } else if (identifier.getKind() == 2) {
            ByteBuf buffer = Unpooled.buffer(2 + identifier.getName().length());
            buffer.writeByte(2);
            buffer.writeByte(identifier.getName().length());
            buffer.writeBytes(identifier.getName().getBytes());
            return buffer;
        } else {
            throw new IllegalArgumentException("Unknown identifier kind: " + identifier.getKind());
        }
    }

    static ByteBuf nameToBytes(String name) {
        ByteBuf buffer = Unpooled.buffer(1 + name.length());
        buffer.writeByte(name.length());
        buffer.writeBytes(name.getBytes());
        return buffer;
    }

    static ByteBuf toBytesAsU64(BigInteger value) {
        ByteBuf buffer = Unpooled.buffer(8, 8);
        byte[] valueAsBytes = value.toByteArray();
        if (valueAsBytes.length > 8) {
            throw new IllegalArgumentException();
        }
        ArrayUtils.reverse(valueAsBytes);
        buffer.writeBytes(valueAsBytes);
        if (valueAsBytes.length < 8) {
            buffer.writeZero(8 - valueAsBytes.length);
        }
        return buffer;
    }

}
