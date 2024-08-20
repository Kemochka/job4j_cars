package cars.repository.owner;

import cars.model.Owner;
import cars.repository.CrudRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HbmOwnerRepositoryTest {
    private static OwnerRepository ownerRepository;

    @BeforeAll
    public static void initRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        ownerRepository = new HbmOwnerRepository(new CrudRepository(sf));
    }

    @Test
    void whenSave() {
        Owner owner = new Owner();
        owner = ownerRepository.save(owner);
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getName()).isEqualTo(owner.getName());
    }

    @Test
    void whenSaveItemAndCheckItemId() {
        Owner owner = new Owner();
        owner = ownerRepository.save(owner);
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(owner);
    }

    @Test
    void whenUpdate() {
        Owner owner = new Owner();
        owner.setName("O");
        ownerRepository.save(owner);
        owner.setName("Owner");
        ownerRepository.update(owner);
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().getName()).isEqualTo("Owner");
    }

    @Test
    void whenTestFindAll() {
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        owner1 = ownerRepository.save(owner1);
        owner2 = ownerRepository.save(owner2);
        Collection<Owner> expected = List.of(owner1, owner2);
        assertThat(ownerRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void whenDelete() {
        Owner owner = new Owner();
        owner = ownerRepository.save(owner);
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertTrue(result.isPresent());
        ownerRepository.deleteById(owner.getId());
        assertThat(ownerRepository.findAll().size()).isEqualTo(0);
    }
}