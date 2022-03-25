package com.preethzcodez.ecommerce.comparators;

import com.preethzcodez.ecommerce.pojo.Category;
import java.util.Comparator;

public class SortByNameCategory implements Comparator<Category> {
    @Override
    public int compare(Category a, Category b) {
        return a.getName().compareTo(b.getName());
    }
}
