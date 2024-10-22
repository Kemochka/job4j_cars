package cars.controller;

import cars.dto.PhotoDto;
import cars.service.photo.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PhotoControllerTest {
    PhotoService photoService;
    PhotoController photoController;
    private MultipartFile testFile;


    @BeforeEach
    public void initService() {
        photoService = mock(PhotoService.class);
        photoController = new PhotoController(photoService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    public void whenGetIdFile() throws Exception {
        var fileDto = new PhotoDto(testFile.getOriginalFilename(), testFile.getBytes());
        when(photoService.findById(1)).thenReturn(Optional.of(fileDto));
        var view = photoController.getById(1);
        assertThat(view).isEqualTo(ResponseEntity.ok(fileDto.getContent()));
    }
}