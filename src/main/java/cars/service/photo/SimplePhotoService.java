package cars.service.photo;

import cars.dto.PhotoDto;
import cars.model.Photo;
import cars.repository.photo.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimplePhotoService implements PhotoService {
    private final PhotoRepository photoRepository;
    private final String storageDirectory;

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SimplePhotoService(PhotoRepository hbmPhotoRepository,
                              @Value("${file.directory}") String storageDirectory) {
        this.photoRepository = hbmPhotoRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    @Override
    public Photo save(PhotoDto photoDto) {
        var path = getNewFilePath(photoDto.getName());
        writeFileBytes(path, photoDto.getContent());
        return photoRepository.save(new Photo(photoDto.getName(), path));
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDto> findById(int id) {
        var photoOptional = photoRepository.findById(id);
        if (photoOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(photoOptional.get().getPath());
        return Optional.of(new PhotoDto(photoOptional.get().getName(), content));
    }

    @Override
    public List<Photo> findByPostId(int postId) {
        return photoRepository.findByPostId(postId);
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        var fileOptional = photoRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            photoRepository.deleteById(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
