package cars.repository.brand;

import cars.model.Brand;

import java.util.Collection;
import java.util.Optional;

public interface BrandRepository {
    Brand save(Brand brand);

    Collection<Brand> findAll();

    Optional<Brand> findById(int id);

    boolean update(Brand brand);

    boolean deleteById(int id);
}
