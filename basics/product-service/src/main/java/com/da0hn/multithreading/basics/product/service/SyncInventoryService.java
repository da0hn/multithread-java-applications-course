package com.da0hn.multithreading.basics.product.service;

import com.da0hn.multithreading.basics.product.service.domain.Inventory;
import com.da0hn.multithreading.basics.product.service.domain.ProductOption;
import com.da0hn.multithreading.basics.product.service.utils.CommonUtil;

public class SyncInventoryService {


  private static final int ADD_INVENTORY_DELAY = 500;

  public Inventory addInventory(final ProductOption option) {
    CommonUtil.delay(ADD_INVENTORY_DELAY);
    return Inventory.builder()
      .count(2L)
      .build();
  }

}
