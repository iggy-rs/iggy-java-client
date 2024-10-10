package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BytesSerializerTest {

    @Nested
    class U64 {

        @Test
        void shouldSerializeMaxValue() {
            // given
            long maxLong = 0xFFFF_FFFF_FFFF_FFFFL;
            var maxU64 = Long.toUnsignedString(maxLong);
            var value = new BigInteger(maxU64);

            // when
            ByteBuf bytesAsU64 = BytesSerializer.toBytesAsU64(value);

            // then
            assertThat(bytesAsU64).isEqualByComparingTo(Unpooled.copyLong(maxLong));
        }

        @Test
        void shouldSerializeZero() {
            // given
            var value = BigInteger.ZERO;
            var zeroAsU64 = Unpooled.buffer(8);
            zeroAsU64.writeZero(8);

            // when
            ByteBuf bytesAsU64 = BytesSerializer.toBytesAsU64(value);

            // then
            assertThat(bytesAsU64).isEqualByComparingTo(zeroAsU64);
        }

        @Test
        void shouldFailForValueBelowZero() {
            // given
            var value = BigInteger.valueOf(-1);

            // when & then
            assertThatThrownBy(() -> BytesSerializer.toBytesAsU64(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    class U128 {

        @Test
        void shouldSerializeMaxValue() {
            // given
            byte[] maxU128 = new byte[17];
            Arrays.fill(maxU128, 1, 17, (byte) 0xFF);

            var value = new BigInteger(maxU128);

            // when
            ByteBuf bytesAsU128 = BytesSerializer.toBytesAsU128(value);

            // then
            assertThat(bytesAsU128).isEqualByComparingTo(Unpooled.wrappedBuffer(maxU128, 1, 16));
        }


        @Test
        void shouldSerializeZero() {
            // given
            var value = BigInteger.ZERO;
            var zeroAsU128 = Unpooled.buffer(8);
            zeroAsU128.writeZero(16);

            // when
            ByteBuf bytesAsU128 = BytesSerializer.toBytesAsU128(value);

            // then
            assertThat(bytesAsU128).isEqualByComparingTo(zeroAsU128);
        }

        @Test
        void shouldFailForValueBelowZero() {
            // given
            var value = BigInteger.valueOf(-1);

            // when & then
            assertThatThrownBy(() -> BytesSerializer.toBytesAsU128(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
