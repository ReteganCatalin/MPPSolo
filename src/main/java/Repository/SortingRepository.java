package repository;


import Model.domain.BaseEntity;

import java.io.Serializable;


public interface SortingRepository<ID extends Serializable, T extends BaseEntity<ID>>
                                    extends IRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

}
