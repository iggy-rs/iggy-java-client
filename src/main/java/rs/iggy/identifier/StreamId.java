package rs.iggy.identifier;

public class StreamId extends Identifier {

    private StreamId(String name, Long id) {
        super(name, id);
    }

    public static StreamId of(String name) {
        return new StreamId(name, null);
    }

    public static StreamId of(Long id) {
        return new StreamId(null, id);
    }

}
