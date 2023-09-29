package rs.iggy.user;

record GlobalPermissions(
        boolean manageServers,
        boolean readServers,
        boolean manageUsers,
        boolean readUsers,
        boolean manageStreams,
        boolean readStreams,
        boolean manageTopics,
        boolean readTopics,
        boolean pollMessages,
        boolean sendMessages
) {
}
