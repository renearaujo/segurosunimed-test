package com.example.api.util;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MapperUtils {

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
        return new ModelMapper().map(source, targetClass);
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
}
