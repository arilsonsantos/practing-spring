package br.com.orion.loja.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * PageableResponseWrapper
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageableResponseWrapper<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageableResponseWrapper(@JsonProperty("content") List<T> content, 
            @JsonProperty("number") int number,
            @JsonProperty("size") int size, 
            @JsonProperty("totalElements") Long totalElements,
            @JsonProperty("pageable") JsonNode pageable, 
            @JsonProperty("last") boolean last,
            @JsonProperty("totalPages") int totalPages, 
            @JsonProperty("sort") JsonNode sort,
            @JsonProperty("first") boolean first, 
            @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);

    }

    public PageableResponseWrapper(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageableResponseWrapper(List<T> content) {
        super(content);
    }

    public PageableResponseWrapper() {
        super(new ArrayList<>());
    }

}