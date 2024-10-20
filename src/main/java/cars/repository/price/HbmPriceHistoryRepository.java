package cars.repository.price;

import cars.model.PriceHistory;
import cars.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriceHistoryRepository implements PriceHistoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.save(priceHistory));
        return priceHistory;
    }

    @Override
    public List<PriceHistory> findAllLastPrice() {
        return crudRepository.query(
                "from PriceHistory where (post.id, created) in (select post.id, MAX(created) from PriceHistory group by post.id)",
                PriceHistory.class);
    }

    @Override
    public List<PriceHistory> findAllLastPriceByPostId(int postId) {
        return crudRepository.query("""
                        FROM PriceHistory ph
                        WHERE post.id = :postId
                        ORDER BY created DESC""", PriceHistory.class,
                Map.of("postId", postId)
        );
    }

    @Override
    public Optional<PriceHistory> findById(int priceHistoryId) {
        return crudRepository.optional("from PriceHistory as ph where ph.id = :id",
                PriceHistory.class,
                Map.of("id", priceHistoryId)
        );
    }

    @Override
    public Optional<PriceHistory> findLastByPostId(int postId) {
        return crudRepository.optional(
                "SELECT ph FROM PriceHistory ph JOIN ph.post p WHERE ph.post.id = :postId AND ph.created = (SELECT MAX(p2.created) FROM PriceHistory p2 WHERE p2.post.id = :postId)",
                PriceHistory.class,
                Map.of("postId", postId)
        );
    }

    @Override
    public boolean deleteAllByPostId(int postId) {
        var result = crudRepository.run(
                "delete from PriceHistory where post.id = :postId",
                Map.of("postId", postId)
        );
        return result > 0;
    }
}
