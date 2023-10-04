package rs.iggy.identifier;

final public class SingleConsumerId extends ConsumerId {

    private SingleConsumerId(String name, Long id) {
        super(name, id);
    }

    public static SingleConsumerId of(String name) {
        return new SingleConsumerId(name, null);
    }

    public static SingleConsumerId of(Long id) {
        return new SingleConsumerId(null, id);
    }

}
