package rs.iggy.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PartitioningKind {
    @JsonProperty("balanced")
    Balanced,
    @JsonProperty("partition_id")
    PartitionId,
    @JsonProperty("messages_key")
    MessagesKey,
}
