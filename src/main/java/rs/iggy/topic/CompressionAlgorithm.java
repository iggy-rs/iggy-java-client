package rs.iggy.topic;

public enum CompressionAlgorithm {
    none(1),
    gzip(2);

    private final Integer code;

    CompressionAlgorithm(Integer code) {
        this.code = code;
    }

    public static CompressionAlgorithm fromCode(byte code) {
        for (CompressionAlgorithm algorithm : values()) {
            if (algorithm.code == code) {
                return algorithm;
            }
        }
        throw new IllegalArgumentException();
    }

    public Integer asCode() {
        return code;
    }
}
