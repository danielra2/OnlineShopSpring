package mycode.onlineshopspring.products.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsQuerryServiceImpl implements ProductsQuerryService{
    private final ProductsRepository productsRepository;
    private final OnlineShopMapper mapper;
    public ProductsQuerryServiceImpl(ProductsRepository productsRepository,OnlineShopMapper mapper){
        this.productsRepository=productsRepository;
        this.mapper=mapper;
    }
    @Override
    public ProductsListResponse findAllProducts(){
        List<Products>productsList=productsRepository.findAll();
        List<ProductsResponse>productsResponseList=mapper.mapProductsListToResponseList(productsList);
        return new ProductsListResponse(productsResponseList);
    }

    @Override
    public Optional<ProductsResponse> findProductById(Long id) {
        return productsRepository.findById(id).map(mapper::mapProductsToProductsResponse);
    }
}