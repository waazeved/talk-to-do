package com.waltsoft.talk_to_do.business.basic;

import com.waltsoft.talk_to_do.entity.BasicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BasicService<T extends BasicEntity, ID> {

    default List<T> findAll() {
        return getRepository().findAll();
    }

    JpaRepository<T, ID> getRepository();

    default List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    default Page<T> findAll(final Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    default Optional<T> findById(final ID id) {
        return getRepository().findById(id);
    }

    default List<T> findByIds(final List<ID> ids) {
        return getRepository().findAllById(ids);
    }

    default boolean exists(final ID id) {
        return getRepository().existsById(id);
    }

    default Long count() {
        return getRepository().count();
    }

    @Transactional
    default void delete(final T entity) {
        getRepository().delete(entity);
    }

    @Transactional
    default void delete(final Collection<? extends T> entities) {
        getRepository().deleteAll(entities);
    }

    @Transactional
    default void deleteAll() {
        getRepository().deleteAll();
    }

    @Transactional
    default void deleteInBatch(final Collection<T> entities) {
        getRepository().deleteAllInBatch(entities);
    }

    @Transactional
    default T save(final T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    default T saveAndFlush(final T entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Transactional
    default List<T> save(final Collection<T> entities) {
        return getRepository().saveAll(entities);
    }
}