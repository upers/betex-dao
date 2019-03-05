package com.vareger.interfaces;

public interface Supplier<T> {
	T get() throws Exception;
}