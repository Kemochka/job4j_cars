package cars.converter;

import cars.dto.CarDto;
import cars.dto.PostDto;
import cars.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public PostDto convertToPostDto(Post post) {
        CarDto carDto = CarConverter.convertToDto(post.getCar());
        return PostDto.builder()
                .id(post.getId())
                .car(carDto)
                .description(post.getDescription())
                .created(post.getCreated())
                .sold(post.isSold())
                .price(post.getPrice())
                .build();
    }
}
