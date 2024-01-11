package com.example.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Builder
@With
@NoArgsConstructor
public class UnimedPagedResponse<T> {
    private long totalElements;
    private long totalElementsThisPage;
    private int pageNumber;
    private int pageIndex;
    private int totalPages;
    @JsonProperty("isFirst")
    private boolean isFirst;
    @JsonProperty("isLast")
    private boolean isLast;
    @JsonProperty("hasNext")
    private boolean hasNext;
    @JsonProperty("hasPrevious")
    private boolean hasPrevious;
    private List<T> data;
}
