package ru.aston.arrayList.sorting;

import java.util.Comparator;

public interface Sorting<E> {
    Object[] sort(Object[] array, Comparator<? super E> c);
}
