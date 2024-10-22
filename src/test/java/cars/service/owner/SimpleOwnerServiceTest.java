package cars.service.owner;

import cars.model.Owner;
import cars.repository.owner.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleOwnerServiceTest {
    private SimpleOwnerService ownerService;
    private OwnerRepository ownerRepository;

    @BeforeEach
    public void init() {
        ownerRepository = mock(OwnerRepository.class);
        ownerService = new SimpleOwnerService(ownerRepository);
    }

    @Test
    public void whenSaveOwner() {
        var owner = new Owner();
        when(ownerRepository.save(owner)).thenReturn(owner);
        var actual = ownerService.save(owner);
        assertThat(actual).isEqualTo(owner);
    }

    @Test
    public void whenFindAllOwners() {
        var owner1 = new Owner();
        var owner2 = new Owner();
        Collection<Owner> owners = List.of(owner1, owner2);
        when(ownerRepository.findAll()).thenReturn(owners);
        var actual = ownerService.findAll();
        assertThat(actual).isEqualTo(owners);
    }

    @Test
    public void whenAddOwnerAndFindById() {
        var engine = new Owner();
        when(ownerRepository.findById(engine.getId())).thenReturn(Optional.of(engine));
        var actual = ownerService.findById(engine.getId());
        assertThat(actual).isEqualTo(Optional.of(engine));
    }

    @Test
    public void whenUpdateOwner() {
        var engine = new Owner();
        when(ownerRepository.update(engine)).thenReturn(true);
        var actual = ownerService.update(engine);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenDeleteOwner() {
        var engine = new Owner();
        when(ownerRepository.deleteById(engine.getId())).thenReturn(true);
        var actual = ownerService.deleteById(engine.getId());
        assertThat(actual).isTrue();
    }
}