package rs.iggy.message;

import java.math.BigInteger;

public record PollingStrategy(PollingKind kind, BigInteger value) {
}
