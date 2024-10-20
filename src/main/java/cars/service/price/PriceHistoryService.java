package cars.service.price;

import cars.model.Post;
import cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryService {
    PriceHistory create(Post post, long price);

    Optional<PriceHistory> findById(int id);

    List<PriceHistory> findAllLastPrice();

    List<PriceHistory> findAllLastPriceByPostId(int postId);

    Optional<PriceHistory> findLastByPostId(int postId);

    boolean update(Post post, long price);

    boolean deleteAllByPostId(int postId);
}
