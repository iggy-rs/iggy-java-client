package rs.iggy.identifier;

import javax.annotation.Nullable;

final public class ConsumerGroupId extends ConsumerId {

    private ConsumerGroupId(@Nullable String name, @Nullable Long id) {
        super(name, id);
    }

    public static ConsumerGroupId of(String name) {
        return new ConsumerGroupId(name, null);
    }

    public static ConsumerGroupId of(Long id) {
        return new ConsumerGroupId(null, id);
    }

}
