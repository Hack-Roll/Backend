package com.hack_roll.hack_roll.dto;

import org.springframework.data.jpa.domain.Specification;
import com.hack_roll.hack_roll.model.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventSpecifications {

    public static Specification<Event> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Event> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Event> hasDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            return criteriaBuilder.between(root.get("date"), startOfDay, endOfDay);
        };
    }

    public static Specification<Event> createdByUserName(String createdByUserName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("createdBy").get("email")), // @TODO replace email with name when name is available
                        "%" + createdByUserName.toLowerCase() + "%"
                );
    }
}