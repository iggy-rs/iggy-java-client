package rs.iggy.identifier;

public sealed abstract class ConsumerId extends Identifier permits SingleConsumerId, ConsumerGroupId {

    protected ConsumerId(String name, Long id) {
        super(name, id);
    }

}
