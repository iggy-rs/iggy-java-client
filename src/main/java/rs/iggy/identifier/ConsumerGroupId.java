package rs.iggy.identifier;

final public class ConsumerGroupId extends ConsumerId {

    private ConsumerGroupId(String name, Long id) {
        super(name, id);
    }

    public static ConsumerGroupId of(String name) {
        return new ConsumerGroupId(name, null);
    }

    public static ConsumerGroupId of(Long id) {
        return new ConsumerGroupId(null, id);
    }

}
