package nazmul.culture.service.IService;


import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Optional;

public interface IGlobalService<T, ID> {

    // get all entities/records from the table
    List<T> findAll();

    // save an object
    T save(T t);

    // find an entity by id
    Optional<T> findById(Long id);




}
