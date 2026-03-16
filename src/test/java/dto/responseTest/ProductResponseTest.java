package dto.responseTest;

import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductResponseTest {

    @Test
    void CalculatedTotalStockValueTest_success(){
        ProductResponseDTO responseDTOTest = new ProductResponseDTO(1L,"Monster","Energy", new BigDecimal("9.99"), 36,
                "Bebidas", 1L);

        BigDecimal totalStockValue = responseDTOTest.getTotalStockValue();

        assertTrue(new BigDecimal("359.64").compareTo(totalStockValue) == 0, "O valor deveria ser 359.64: " + totalStockValue);
    }

}
