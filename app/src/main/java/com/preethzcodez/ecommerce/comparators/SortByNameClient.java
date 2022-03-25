package com.preethzcodez.ecommerce.comparators;

import com.preethzcodez.ecommerce.pojo.Category;
import com.preethzcodez.ecommerce.pojo.Client;

import java.util.Comparator;

public class SortByNameClient implements Comparator<Client> {
    @Override
    public int compare(Client a, Client b) {
        return a.getName().compareTo(b.getName());
    }
}
