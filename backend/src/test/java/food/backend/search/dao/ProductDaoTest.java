import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import food.backend.search.config.ElasticsearchConfig;
import food.backend.search.dao.ProductDao;
import food.backend.search.dto.ProductDto;
import food.backend.search.model.KeywordAndNutrientEs;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductDaoTest {

    @Mock
    private ElasticsearchConfig elasticsearchConfig;

    @Mock
    private RestHighLevelClient restHighLevelClient;

    @InjectMocks
    private ProductDao productDao;

    @BeforeEach
    void setUp() throws IOException {
        when(elasticsearchConfig.client()).thenReturn(restHighLevelClient);
    }

    @Test
    void testGetProductByKeywordAndNutrient() throws IOException {
        // Given
        KeywordAndNutrientEs params = new KeywordAndNutrientEs( "100", "1",
                "100",
                "1",
                "20",
                "0",
                "2",
                "0"); // Initialize with test data
        SearchResponse mockResponse = mock(SearchResponse.class);
        SearchHits mockHits = mock(SearchHits.class);
        SearchHit[] mockHitArray = new SearchHit[0]; // Populate with test data

        when(restHighLevelClient.search(any(SearchRequest.class), any())).thenReturn(mockResponse);
        when(mockResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(mockHitArray);

        // When
        List<ProductDto> result = productDao.getProductByKeywordAndNutrient(params);

        // Then
        assertNotNull(result);
        verify(restHighLevelClient, times(1)).search(any(SearchRequest.class), any());
    }

    // Additional tests for other methods
}
