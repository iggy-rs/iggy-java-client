package rs.iggy.identifier;

import javax.annotation.Nullable;

public sealed abstract class ConsumerId extends Identifier permits SingleConsumerId, ConsumerGroupId {

    protected ConsumerId(@Nullable String name, @Nullable Long id) {
        super(name, id);
    }

}
