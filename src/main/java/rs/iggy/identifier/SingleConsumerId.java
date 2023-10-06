package rs.iggy.identifier;

import javax.annotation.Nullable;

final public class SingleConsumerId extends ConsumerId {

    private SingleConsumerId(@Nullable String name, @Nullable Long id) {
        super(name, id);
    }

    public static SingleConsumerId of(String name) {
        return new SingleConsumerId(name, null);
    }

    public static SingleConsumerId of(Long id) {
        return new SingleConsumerId(null, id);
    }

}
