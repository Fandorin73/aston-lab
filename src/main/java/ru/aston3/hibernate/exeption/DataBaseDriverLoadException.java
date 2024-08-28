package ru.aston3.hibernate.exeption;

public class DataBaseDriverLoadException extends RuntimeException {
    public DataBaseDriverLoadException(String message) {
        super(message);
    }
}
