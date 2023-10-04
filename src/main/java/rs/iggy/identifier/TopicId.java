package rs.iggy.identifier;

public class TopicId extends Identifier {

    private TopicId(String name, Long id) {
        super(name, id);
    }

    public static TopicId of(String name) {
        return new TopicId(name, null);
    }

    public static TopicId of(Long id) {
        return new TopicId(null, id);
    }

}
