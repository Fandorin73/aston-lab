package ru.aston.arrayList.arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ArrayListCustomTest {
    private ListCustom<String> listString;
    private ListCustom<Integer> listInteger;

    @BeforeEach
    public void beforeClassFunction() {
        listString = new ArrayListCustom<>();
        String[] a = {"a", "b", "cd", "d", "abc", "ab", "dcb", "abcd", "dcba"};
        listString.addAll(Arrays.asList(a));

        listInteger = new ArrayListCustom<>();
        listInteger.addAll(Arrays.asList(-8, 25, 30, 0, 11, 1, 2, 4, 5, 6, -2, 9));
    }

    @Test
    public void sizeTest() {
        assertEquals(9, listString.size());

        assertEquals(12, listInteger.size());
    }

    @Test
    void clear() {
        listString.clear();
        assertEquals(0, listString.size());
    }

    @Test
    void get() {
        assertEquals(11, listInteger.get(4));

        assertEquals("dcba", listString.get(8));
    }

    @Test
    void isEmptyTest() {
        listInteger.clear();
        assertTrue(listInteger.isEmpty());

        assertFalse(listString.isEmpty());
    }

    @Test
    void removeTest() {
        listString.remove(1);
        listString.remove("a");

        ListCustom<String> listForTest = new ArrayListCustom<>();
        listForTest.addAll(Arrays.asList("cd", "d", "abc", "ab", "dcb", "abcd", "dcba"));

        assertEquals(listForTest, listString);
    }

    @Test
    void sortTest() {
        ListCustom<String> listStringForTestSort = new ArrayListCustom<>();
        listStringForTestSort.addAll(Arrays.asList("a", "ab", "abc", "abcd", "b", "cd", "d", "dcb", "dcba"));
        listString.sort(Comparator.naturalOrder());
        assertEquals(listStringForTestSort, listString);

        ListCustom<Integer> listIntegerForTestSort = new ArrayListCustom<>();
        listIntegerForTestSort.addAll(Arrays.asList(30, 25, 11, 9, 6, 5, 4, 2, 1, 0, -2, -8));
        listInteger.sort(Comparator.reverseOrder());
        assertEquals(listIntegerForTestSort, listInteger);

        class Car {
            public String getColor() {
                return color;
            }

            public int getMileage() {
                return mileage;
            }

            String color;
            int mileage;

            public Car(String color, int mileage) {
                this.color = color;
                this.mileage = mileage;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Car car = (Car) o;
                return mileage == car.mileage && Objects.equals(color, car.color);
            }

            @Override
            public int hashCode() {
                int result = Objects.hashCode(color);
                result = 31 * result + mileage;
                return result;
            }

            @Override
            public String toString() {
                return "Car{" +
                        "color='" + color + '\'' +
                        ", mileage=" + mileage +
                        '}';
            }
        }
        Car blueCar = new Car("Синий", 2222);
        Car redCar = new Car("Красный", 8000);
        Car blackCar = new Car("Черный", 2000);

        ListCustom<Car> listCar = new ArrayListCustom<>();
        listCar.add(blueCar);
        listCar.add(redCar);
        listCar.add(blackCar);

        listCar.sort(Comparator.comparing(Car::getMileage));

        ListCustom<Car> listCarSortByMileage = new ArrayListCustom<>();
        listCarSortByMileage.add(blackCar);
        listCarSortByMileage.add(blueCar);
        listCarSortByMileage.add(redCar);

        assertEquals(listCarSortByMileage, listCar);

        listCar.sort(Comparator.comparing(Car::getColor));

        ListCustom<Car> listCarSortByColor = new ArrayListCustom<>();
        listCarSortByColor.add(redCar);
        listCarSortByColor.add(blueCar);
        listCarSortByColor.add(blackCar);

        assertEquals(listCarSortByColor, listCar);
    }


}