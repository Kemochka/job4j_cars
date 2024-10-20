package cars.service.price;

import cars.model.Post;
import cars.model.PriceHistory;
import cars.repository.price.PriceHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    @Override
    public PriceHistory create(Post post, long price) {
        var priceHistory = new PriceHistory();
        priceHistory.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        priceHistory.setAfter(price);
        priceHistory.setPost(post);
        return priceHistoryRepository.create(priceHistory);
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return priceHistoryRepository.findById(id);
    }

    @Override
    public List<PriceHistory> findAllLastPrice() {
        return priceHistoryRepository.findAllLastPrice();
    }

    @Override
    public List<PriceHistory> findAllLastPriceByPostId(int postId) {
        return priceHistoryRepository.findAllLastPriceByPostId(postId);
    }

    @Override
    public Optional<PriceHistory> findLastByPostId(int postId) {
        return priceHistoryRepository.findLastByPostId(postId);
    }

    @Override
    public boolean update(Post post, long price) {
        var lastPrice = priceHistoryRepository.findLastByPostId(post.getId());
        if (lastPrice.isPresent()) {
            PriceHistory priceHistory = lastPrice.get();
            priceHistory.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
            priceHistory.setBefore(priceHistory.getAfter());
            priceHistory.setAfter(price);
            priceHistory.setPost(post);
            priceHistoryRepository.create(priceHistory);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllByPostId(int postId) {
        return priceHistoryRepository.deleteAllByPostId(postId);
    }
}
