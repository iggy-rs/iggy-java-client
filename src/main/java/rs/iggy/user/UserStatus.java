package rs.iggy.user;

public enum UserStatus {
    Active(1),
    Inactive(2);

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public static UserStatus fromCode(int code) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.code == code) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("Invalid user status: " + code);
    }
}
