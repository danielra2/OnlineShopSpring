package mycode.onlineshopspring.orderDetails.service;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsDto;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import mycode.onlineshopspring.orderDetails.repository.OrderDetailsRepository;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class OrderDetailsCommandServiceImpl implements OrderDetailsCommandService{
    private final OrderDetailsRepository repository;
    private final ProductsRepository productsRepository;
    private final OnlineShopMapper mapper;
    public OrderDetailsCommandServiceImpl(OrderDetailsRepository repository,ProductsRepository productsRepository,OnlineShopMapper mapper){
        this.repository=repository;
        this.productsRepository=productsRepository;
        this.mapper=mapper;
    }
    @Transactional
    @Override
    public OrderDetailsResponse createOrderDetails(OrderDetailsDto dto)throws ProductDoesntExistException{
        Products product=productsRepository.findById(dto.productId()).orElseThrow(ProductDoesntExistException::new);
        OrderDetails e=mapper.mapOrderDetailsDtoToOrderDetails(dto);
        e.setProduct(product);
        OrderDetails saved=repository.save(e);
        return mapper.mapOrderDetailsToResponse(saved);
    }
}
