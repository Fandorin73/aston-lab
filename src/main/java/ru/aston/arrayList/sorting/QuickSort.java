package ru.aston.arrayList.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class QuickSort<E> implements Sorting<E> {
    @Override
    public Object[] sort(Object[] array, Comparator<? super E> c) {
        quickSort(array, 0,
                (int) Arrays.stream(array)
                        .filter(Objects::nonNull)
                        .count() - 1,
                c);
        return array;
    }

    private void quickSort(Object[] array, int from, int to, Comparator<? super E> c) {
        if (from < to) {

            int divideIndex = partition(array, from, to, c);

            quickSort(array, from, divideIndex - 1, c);

            quickSort(array, divideIndex, to, c);
        }
    }

    private int partition(Object[] arr, int from, int to, Comparator<? super E> c) {
        int rightIndex = to;
        int leftIndex = from;

        Object pivot = arr[from + (to - from) / 2];
        while (leftIndex <= rightIndex) {

            while (c.compare((E) arr[leftIndex],
                    (E) pivot) < 0) {
                leftIndex++;
                for (int i = 0; i < arr.length; i++) {
                }
            }

            while (c.compare((E) arr[rightIndex],
                    (E) pivot) > 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(arr, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    private static void swap(Object[] array, int index1, int index2) {
        Object tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}
