package com.example.api.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final ModelMapper modelMapper;

    /**
     * Converts the  source to the target class using {@link ModelMapper}
     *
     * @author René Araújo Vasconcelos - 1/8/2024 - 8:32 PM
     * @param source source to be converts
     * @param targetClass target class
     * @return the target class
     * @param <S> source type
     * @param <T> target type
     */
    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    /**
     * Converts the source list to target type
     *
     * @author René Araújo Vasconcelos - 1/8/2024 - 8:34 PM
     * @param source list to be converted
     * @param targetClass target class
     * @return @{@link List} with the target class
     * @param <S> source type
     * @param <T> target type
     */
    public <S, T> List<T> mapAll(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <T> void merge(T source, T target) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

}
