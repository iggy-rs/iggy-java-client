package rs.iggy.identifier;

public class UserId extends Identifier {

    private UserId(String name, Long id) {
        super(name, id);
    }

    public static UserId of(String name) {
        return new UserId(name, null);
    }

    public static UserId of(Long id) {
        return new UserId(null, id);
    }

}
