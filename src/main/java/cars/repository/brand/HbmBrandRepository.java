package cars.repository.brand;

import cars.model.Brand;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmBrandRepository implements BrandRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = Logger.getLogger(HbmBrandRepository.class);

    @Override
    public Brand save(Brand brand) {
        try {
            crudRepository.run(session -> session.save(brand));
        } catch (Exception e) {
            LOGGER.error("Exception during save brand", e);
        }
        return brand;
    }

    @Override
    public Collection<Brand> findAll() {
        return crudRepository.query("from Brand", Brand.class);
    }

    @Override
    public Optional<Brand> findById(int id) {
        return crudRepository.optional("from Brand where id = :fId", Brand.class, Map.of("fId", id));
    }

    @Override
    public boolean update(Brand brand) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(brand));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during update brand");
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run("delete from Brand where id = :fId", Map.of("fId", id));
            result = true;
        } catch (Exception e) {
            LOGGER.error("Exception during delete brand by id");
        }
        return result;
    }
}
