package com.accounts.api;

import java.util.List;
import java.util.Optional;

/**
 * Created by pasha on 15.02.18.
 */
public interface ICrudAPI<T> {

    List<T> findAll();

    Optional<T> findById(long id);

    void deleteById(long id);

    T save(T value);

}
