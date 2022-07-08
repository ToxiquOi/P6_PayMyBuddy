package fr.ocr.paymybuddy.builder;

public abstract class AbstractBuilder<T> {
    protected T instance;

    public T build() {
        return instance;
    }
}
