package com.da0hn.multithreading.basics.product.service.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProductOption(
    Long productOptionId,
    String size,
    String color,
    Double price,
    Inventory inventory
) {
}
