package cars.repository.brand;

import cars.model.Brand;
import cars.repository.CrudRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HbmBrandRepositoryTest {
    private static BrandRepository brandRepository;

    @BeforeAll
    public static void initRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        brandRepository = new HbmBrandRepository(new CrudRepository(sf));
    }

    @Test
    void whenSave() {
        Brand brand = new Brand();
        brandRepository.save(brand);
        Optional<Brand> result = brandRepository.findById(brand.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getId()).isEqualTo(brand.getId());
    }

    @Test
    void whenUpdate() {
        Brand brand = new Brand();
        brand.setName("br");
        brandRepository.save(brand);
        brand.setName("brand");
        brandRepository.update(brand);
        Optional<Brand> result = brandRepository.findById(brand.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getName()).isEqualTo("brand");
    }

    @Test
    void whenFindAll() {
        Brand brand1 = new Brand();
        Brand brand2 = new Brand();
        brandRepository.save(brand1);
        brandRepository.save(brand2);
        Collection<Brand> brands = List.of(brand1, brand2);
        assertThat(brandRepository.findAll()).isEqualTo(brands);
    }

    @Test
    void whenDelete() {
        Brand brand = new Brand();
        brandRepository.save(brand);
        Optional<Brand> result = brandRepository.findById(brand.getId());
        assertTrue(result.isPresent());
        brandRepository.deleteById(brand.getId());
        assertThat(brandRepository.findById(brand.getId())).isEqualTo(Optional.empty());
    }
}