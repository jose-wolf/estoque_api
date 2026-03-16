package dto.responseTest;

import com.josewolf.estoque_api.dto.response.CategoryResponseDTO;
import com.josewolf.estoque_api.dto.response.ProductResponseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryResponseTest {

    @Test
    void CalculatedCategoryValue_success(){
        ProductResponseDTO responseDTOTest = new ProductResponseDTO(1L,"Monster","Energy", new BigDecimal("10.0"), 5,
                "Bebidas", 1L);

        ProductResponseDTO responseDTOTest2 = new ProductResponseDTO(1L,"Monster","Energy Drink", new BigDecimal("10.0"), 36,
                "Bebidas", 1L);

        List<ProductResponseDTO> responseDTOTestList = new ArrayList<>();
        responseDTOTestList.add(responseDTOTest);
        responseDTOTestList.add(responseDTOTest2);

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(1L, "Bebidas", responseDTOTestList);

        BigDecimal categoryValue = categoryResponseDTO.getCategoryValue();

        assertTrue(new BigDecimal("410.00").compareTo(categoryValue) == 0, "O valor deveria ser 410.00");
    }

}
