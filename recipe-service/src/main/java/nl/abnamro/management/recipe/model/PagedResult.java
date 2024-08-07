package nl.abnamro.management.recipe.model;

import java.util.List;

/**
 * This is response of Get all controller and controlled by Pages to prevent loading of high volume data
 * @param data
 * @param totalElements
 * @param pageNumber
 * @param totalPages
 * @param isFirst
 * @param isLast
 * @param hasNext
 * @param hasPrevious
 * @param <T>
 */
public record PagedResult<T>(
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {}
