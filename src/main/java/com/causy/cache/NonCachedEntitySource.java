package com.causy.cache;

@FunctionalInterface
public interface NonCachedEntitySource {
    Object get();
}
