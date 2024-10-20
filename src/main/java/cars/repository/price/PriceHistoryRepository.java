package cars.repository.price;

import cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository {
    PriceHistory create(PriceHistory priceHistory);

    List<PriceHistory> findAllLastPrice();

    List<PriceHistory> findAllLastPriceByPostId(int postId);

    Optional<PriceHistory> findById(int priceHistoryId);

    Optional<PriceHistory> findLastByPostId(int postId);

    boolean deleteAllByPostId(int postId);
}
