package rs.iggy.system;

public record Stats(
        String processId,
        float cpuUsage,
        long memoryUsage,
        long totalMemory,
        long availableMemory,
        long runTime,
        long startTime,
        long readBytes,
        long writtenBytes,
        long messagesSizeBytes,
        int streamsCount,
        int topicsCount,
        int partitionsCount,
        int segmentsCount,
        long messagesCount,
        int clientsCount,
        int consumerGroupsCount,
        String hostname,
        String osName,
        String osVersion,
        String kernelVersion
) {
}
