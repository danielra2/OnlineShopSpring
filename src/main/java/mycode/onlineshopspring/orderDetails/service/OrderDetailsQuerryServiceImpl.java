package mycode.onlineshopspring.orderDetails.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import mycode.onlineshopspring.orderDetails.repository.OrderDetailsRepository;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsListResponse;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public OrderDetailsListResponse findAllOrderDetails(int page, int size){
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.max(size,1), Sort.by("id"));
        Page<OrderDetails>orderDetailsPage=orderDetailsRepository.findAll(pageable);
        List<OrderDetailsResponse>orderDetailsResponseList=mapper.mapOrderDetailsListToResponseList(orderDetailsPage.getContent());
        return new OrderDetailsListResponse(
                orderDetailsResponseList,
                orderDetailsPage.getTotalElements(),
                orderDetailsPage.getTotalPages(),
                orderDetailsPage.getNumber(),
                orderDetailsPage.getSize());
    }
}
