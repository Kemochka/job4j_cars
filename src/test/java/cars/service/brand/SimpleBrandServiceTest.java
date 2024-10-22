package cars.service.brand;

import cars.model.Brand;
import cars.repository.brand.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleBrandServiceTest {
    private SimpleBrandService brandService;
    private BrandRepository brandRepository;

    @BeforeEach
    public void init() {
        brandRepository = mock(BrandRepository.class);
        brandService = new SimpleBrandService(brandRepository);
    }

    @Test
    public void whenSaveBrand() {
        var brand = new Brand();
        when(brandRepository.save(brand)).thenReturn(brand);
        var actual = brandService.save(brand);
        assertThat(actual).isEqualTo(brand);
    }

    @Test
    public void whenFindAllBrands() {
        var brand1 = new Brand();
        var brand2 = new Brand();
        Collection<Brand> brands = List.of(brand1, brand2);
        when(brandRepository.findAll()).thenReturn(brands);
        var actual = brandService.findAll();
        assertThat(actual).isEqualTo(brands);
    }

    @Test
    public void whenAddBrandAndFindById() {
        var brand = new Brand();
        when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));
        var actual = brandService.findById(brand.getId());
        assertThat(actual).isEqualTo(Optional.of(brand));
    }

    @Test
    public void whenUpdateBrand() {
        var brand = new Brand();
        when(brandRepository.update(brand)).thenReturn(true);
        var actual = brandService.update(brand);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenDeleteBrand() {
        var brand = new Brand();
        when(brandRepository.deleteById(brand.getId())).thenReturn(true);
        var actual = brandService.deleteById(brand.getId());
        assertThat(actual).isTrue();
    }
}