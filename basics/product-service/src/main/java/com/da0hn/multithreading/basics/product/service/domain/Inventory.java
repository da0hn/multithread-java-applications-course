package com.da0hn.multithreading.basics.product.service.domain;

import lombok.Builder;

@Builder
public record Inventory(
    Long count
) {
}
