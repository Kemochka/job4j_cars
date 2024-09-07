package cars.service.owner;

import cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

public interface OwnerService {
    Owner save(Owner owner);

    Optional<Owner> findById(int id);

    Collection<Owner> findAll();

    boolean update(Owner owner);

    boolean deleteById(int id);
}
