package cars.repository.owner;

import cars.model.Owner;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = Logger.getLogger(HbmOwnerRepository.class);

    @Override
    public Owner save(Owner owner) {
        try {
            crudRepository.run(session -> session.save(owner));
        } catch (Exception e) {
            LOGGER.error("Exception during save owner");
        }
        return owner;
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional("from Owner where id = :fId", Owner.class, Map.of("fId", id));
    }

    @Override
    public Collection<Owner> findAll() {
        return crudRepository.query("from Owner", Owner.class);
    }

    @Override
    public boolean update(Owner owner) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(owner));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during update owner");
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("delete from Owner where id = :fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during delete owner by id", e);
        }
        return result;
    }
}
