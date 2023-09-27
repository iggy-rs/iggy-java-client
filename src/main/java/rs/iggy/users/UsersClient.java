package rs.iggy.users;

public interface UsersClient {

    IdentityInfo login(String username, String password);

}
