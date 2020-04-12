package Repository;


import Repository.IRepository;
import Repository.Sort;
import Model.domain.BaseEntity;

import java.io.Serializable;

/**
 * Created by radu.
 */
public interface SortingRepository<ID, T extends BaseEntity<ID>>
                                    extends IRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

}