package rs.iggy.system;

import java.math.BigInteger;

public record Stats(
        Long processId,
        Float cpuUsage,
        BigInteger memoryUsage,
        BigInteger totalMemory,
        BigInteger availableMemory,
        BigInteger runTime,
        BigInteger startTime,
        BigInteger readBytes,
        BigInteger writtenBytes,
        BigInteger messagesSizeBytes,
        Long streamsCount,
        Long topicsCount,
        Long partitionsCount,
        Long segmentsCount,
        BigInteger messagesCount,
        Long clientsCount,
        Long consumerGroupsCount,
        String hostname,
        String osName,
        String osVersion,
        String kernelVersion
) {
}
