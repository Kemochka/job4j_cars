package cars.converter;

import cars.dto.CarDto;
import cars.dto.PostDto;
import cars.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public PostDto convertToPostDto(Post post) {
        CarDto carDto = CarConverter.convertToDto(post.getCar());
        return new PostDto(
                post.getId(),
                carDto,
                post.getDescription(),
                post.getCreated(),
                post.isSold(),
                post.getPrice()
        );
    }
}
