package mycode.onlineshopspring.products.service;

import mycode.onlineshopspring.common.pagination.PaginationUtils;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.products.dto.ProductsListResponse;
import mycode.onlineshopspring.products.dto.ProductsResponse;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static mycode.onlineshopspring.common.pagination.PaginationUtils.fetchPage;

@Service
public class ProductsQuerryServiceImpl implements ProductsQuerryService{
    private final ProductsRepository productsRepository;
    private final OnlineShopMapper mapper;
    public ProductsQuerryServiceImpl(ProductsRepository productsRepository,OnlineShopMapper mapper){
        this.productsRepository=productsRepository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public ProductsListResponse findAllProducts(int page, int size){
        Pageable pageable = PaginationUtils.sanitize(page, size, Sort.by("id"));
        Page<Products>productsPage=fetchPage(productsRepository::findAll,pageable);
        List<ProductsResponse>productsResponseList=mapper.mapProductsListToResponseList(productsPage.getContent());
        return new ProductsListResponse(
                productsResponseList,
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                productsPage.getNumber(),
                productsPage.getSize());
    }
}
