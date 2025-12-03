package mycode.onlineshopspring.orders.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orders.models.Orders;
import mycode.onlineshopspring.orders.repository.OrdersRepository;
import mycode.onlineshopspring.orders.dto.OrdersListResponse;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrdersQuerryServiceImpl implements OrdersQuerryService{
    private final OrdersRepository ordersRepository;
    private final OnlineShopMapper mapper;
    public OrdersQuerryServiceImpl(OrdersRepository ordersRepository,OnlineShopMapper mapper){
        this.ordersRepository=ordersRepository;
        this.mapper=mapper;
    }
    @Override
    public OrdersListResponse findAllOrders(){
        List<Orders>ordersList=ordersRepository.findAll();
        List<OrdersResponse>ordersResponseList=mapper.mapOrdersListToResponseList(ordersList);
        return new OrdersListResponse(ordersResponseList);
    }
}