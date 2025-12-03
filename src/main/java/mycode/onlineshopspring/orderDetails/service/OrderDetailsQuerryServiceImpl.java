package mycode.onlineshopspring.orderDetails.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import mycode.onlineshopspring.orderDetails.repository.OrderDetailsRepository;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsListResponse;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrderDetailsQuerryServiceImpl implements OrderDetailsQuerryService{
    private final OrderDetailsRepository orderDetailsRepository;
    private final OnlineShopMapper mapper;
    public OrderDetailsQuerryServiceImpl(OrderDetailsRepository orderDetailsRepository,OnlineShopMapper mapper){
        this.orderDetailsRepository=orderDetailsRepository;
        this.mapper=mapper;
    }
    @Override
    public OrderDetailsListResponse findAllOrderDetails(){
        List<OrderDetails>orderDetailsList=orderDetailsRepository.findAll();
        List<OrderDetailsResponse>orderDetailsResponseList=mapper.mapOrderDetailsListToResponseList(orderDetailsList);
        return new OrderDetailsListResponse(orderDetailsResponseList);
    }
}