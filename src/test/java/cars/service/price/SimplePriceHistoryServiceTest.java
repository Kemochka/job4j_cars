package cars.service.price;

import cars.model.Post;
import cars.model.PriceHistory;
import cars.repository.price.PriceHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimplePriceHistoryServiceTest {
    private SimplePriceHistoryService priceHistoryService;
    private PriceHistoryRepository priceHistoryRepository;

    @BeforeEach
    public void init() {
        priceHistoryRepository = mock(PriceHistoryRepository.class);
        priceHistoryService = new SimplePriceHistoryService(priceHistoryRepository);
    }

    @Test
    public void whenCreatePriceHistoryThenReturnSavedPriceHistory() {
        var post = new Post();
        var price = 100L;
        var priceHistory = new PriceHistory();
        priceHistory.setPost(post);
        priceHistory.setAfter(price);
        when(priceHistoryRepository.create(any(PriceHistory.class))).thenReturn(priceHistory);
        var actual = priceHistoryService.create(post, price);
        assertThat(actual.getPost()).isEqualTo(post);
        assertThat(actual.getAfter()).isEqualTo(price);
        assertThat(actual.getCreated()).isNotNull();
    }

    @Test
    public void whenFindByIdThenReturnPriceHistory() {
        var priceHistory = new PriceHistory();
        priceHistory.setId(1);
        when(priceHistoryRepository.findById(1)).thenReturn(Optional.of(priceHistory));
        var actual = priceHistoryService.findById(1);
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(priceHistory);
    }

    @Test
    public void whenFindByIdThenReturnEmpty() {
        when(priceHistoryRepository.findById(1)).thenReturn(Optional.empty());
        var actual = priceHistoryService.findById(1);
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenFindAllLastPriceThenReturnPriceHistoryList() {
        var priceHistory1 = new PriceHistory();
        var priceHistory2 = new PriceHistory();
        var priceHistories = List.of(priceHistory1, priceHistory2);
        when(priceHistoryRepository.findAllLastPrice()).thenReturn(priceHistories);
        var actual = priceHistoryService.findAllLastPrice();
        assertThat(actual).isEqualTo(priceHistories);
    }

    @Test
    public void whenFindAllLastPriceByPostIdThenReturnPriceHistoryList() {
        var postId = 1;
        var priceHistory1 = new PriceHistory();
        var priceHistory2 = new PriceHistory();
        var priceHistories = List.of(priceHistory1, priceHistory2);
        when(priceHistoryRepository.findAllLastPriceByPostId(postId)).thenReturn(priceHistories);
        var actual = priceHistoryService.findAllLastPriceByPostId(postId);
        assertThat(actual).isEqualTo(priceHistories);
    }

    @Test
    public void whenFindLastByPostIdThenReturnPriceHistory() {
        var postId = 1;
        var priceHistory = new PriceHistory();
        when(priceHistoryRepository.findLastByPostId(postId)).thenReturn(Optional.of(priceHistory));
        var actual = priceHistoryService.findLastByPostId(postId);
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(priceHistory);
    }

    @Test
    public void whenFindLastByPostIdThenReturnEmpty() {
        var postId = 1;
        when(priceHistoryRepository.findLastByPostId(postId)).thenReturn(Optional.empty());
        var actual = priceHistoryService.findLastByPostId(postId);
        assertThat(actual).isEmpty();
    }

    @Test
    public void whenUpdatePriceHistoryThenReturnTrue() {
        var post = new Post();
        post.setId(1);
        var price = 150L;
        var oldPriceHistory = new PriceHistory();
        oldPriceHistory.setAfter(100L);
        when(priceHistoryRepository.findLastByPostId(post.getId())).thenReturn(Optional.of(oldPriceHistory));
        when(priceHistoryRepository.create(any(PriceHistory.class))).thenReturn(oldPriceHistory);
        var actual = priceHistoryService.update(post, price);
        assertThat(actual).isTrue();
        assertThat(oldPriceHistory.getBefore()).isEqualTo(100L);
        assertThat(oldPriceHistory.getAfter()).isEqualTo(price);
        assertThat(oldPriceHistory.getCreated()).isNotNull();
    }

    @Test
    public void whenUpdatePriceHistoryThenReturnFalse() {
        var post = new Post();
        post.setId(1);
        when(priceHistoryRepository.findLastByPostId(post.getId())).thenReturn(Optional.empty());
        var actual = priceHistoryService.update(post, 150L);
        assertThat(actual).isFalse();
    }

    @Test
    public void whenDeleteAllByPostIdThenReturnTrue() {
        when(priceHistoryRepository.deleteAllByPostId(1)).thenReturn(true);
        var actual = priceHistoryService.deleteAllByPostId(1);
        assertThat(actual).isTrue();
    }

    @Test
    public void whenDeleteAllByPostIdThenReturnFalse() {
        when(priceHistoryRepository.deleteAllByPostId(1)).thenReturn(false);
        var actual = priceHistoryService.deleteAllByPostId(1);
        assertThat(actual).isFalse();
    }
}