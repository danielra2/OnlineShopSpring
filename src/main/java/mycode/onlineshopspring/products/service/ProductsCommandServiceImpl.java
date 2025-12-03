package mycode.onlineshopspring.products.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.products.dto.ProductsDto;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
@Service
public class ProductsCommandServiceImpl implements ProductsCommandService{
    private final ProductsRepository repository;
    private final OnlineShopMapper mapper;
    public ProductsCommandServiceImpl(ProductsRepository repository,OnlineShopMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }
    @Transactional
    @Override
    public ProductsResponse createProduct(ProductsDto dto){
        Products e=mapper.mapProductsDtoToProducts(dto);
        Products saved=repository.save(e);
        return mapper.mapProductsToProductsResponse(saved);
    }
}