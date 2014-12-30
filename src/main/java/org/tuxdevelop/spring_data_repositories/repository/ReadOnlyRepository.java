package org.tuxdevelop.spring_data_repositories.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoRepositoryBean
public interface ReadOnlyRepository<T,ID extends Serializable> extends Repository<T,ID>{

    T findOne(ID id);
    Iterable<T> findAll();
    @Transactional(timeout = 10)
    Iterable<T> findAll(Iterable<ID> ids);
    Page<T> findAll(Pageable pageable);
    Iterable<T> findAll(Sort sort);

}
