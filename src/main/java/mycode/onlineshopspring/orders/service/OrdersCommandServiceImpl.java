package mycode.onlineshopspring.orders.service;
import mycode.onlineshopspring.exceptions.CustomerDoesntExistException;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orders.models.Orders;
import mycode.onlineshopspring.orders.dto.OrdersDto;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import mycode.onlineshopspring.orders.repository.OrdersRepository;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Set;
@Service
public class OrdersCommandServiceImpl implements OrdersCommandService{
    private final OrdersRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductsRepository productsRepository;
    private final OnlineShopMapper mapper;
    public OrdersCommandServiceImpl(OrdersRepository repository,CustomerRepository customerRepository,ProductsRepository productsRepository,OnlineShopMapper mapper){
        this.repository=repository;
        this.customerRepository=customerRepository;
        this.productsRepository=productsRepository;
        this.mapper=mapper;
    }
    @Transactional
    @Override
    public OrdersResponse createOrder(Long customerId, OrdersDto dto)throws CustomerDoesntExistException{
        Customer customer=customerRepository.findById(customerId).orElseThrow(CustomerDoesntExistException::new);
        Orders order=mapper.mapOrdersDtoToOrders(dto);
        Set<OrderDetails>detailsSet=mapper.mapOrderDetailsDtoSetToEntitySet(dto.orderDetailsSet());

        order.setCustomer(customer);
        detailsSet.forEach(detail->{
            detail.setOrder(order);
            detail.setProduct(productsRepository.findById(dto.orderDetailsSet().stream().filter(d->d.sku().equals(detail.getSku())).findFirst().orElseThrow(ProductDoesntExistException::new).productId()).orElseThrow(ProductDoesntExistException::new));
            order.getOrderDetailsSet().add(detail);
        });

        Orders savedOrder=repository.save(order);
        return mapper.mapOrdersToOrdersResponse(savedOrder);
    }
}