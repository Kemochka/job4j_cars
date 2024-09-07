package cars.service.owner;

import cars.model.Owner;
import cars.repository.owner.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleOwnerService implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Collection<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public boolean update(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public boolean deleteById(int id) {
        return ownerRepository.deleteById(id);
    }
}
