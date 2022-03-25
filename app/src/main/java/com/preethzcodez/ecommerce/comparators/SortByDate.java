package com.preethzcodez.ecommerce.comparators;

import com.preethzcodez.ecommerce.objects.OrdersDate;

import java.util.Comparator;

public class SortByDate implements Comparator<OrdersDate> {

    @Override
    public int compare(OrdersDate o1, OrdersDate o2) {
        return o2.getSort() - (o1.getSort());
    }
}
