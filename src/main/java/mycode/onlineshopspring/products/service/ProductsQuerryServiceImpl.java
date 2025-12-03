package mycode.onlineshopspring.products.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductsQuerryServiceImpl implements ProductsQuerryService{
    private final ProductsRepository productsRepository;
    private final OnlineShopMapper mapper;
    public ProductsQuerryServiceImpl(ProductsRepository productsRepository,OnlineShopMapper mapper){
        this.productsRepository=productsRepository;
        this.mapper=mapper;
    }
    @Override
    public ProductsListResponse findAllProducts(int page, int size){
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.max(size,1), Sort.by("id"));
        Page<Products>productsPage=productsRepository.findAll(pageable);
        List<ProductsResponse>productsResponseList=mapper.mapProductsListToResponseList(productsPage.getContent());
        return new ProductsListResponse(
                productsResponseList,
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.getNumber(),
                productsPage.getSize());
    }
}
