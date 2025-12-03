package mycode.onlineshopspring.orders.service;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orders.models.Orders;
import mycode.onlineshopspring.orders.repository.OrdersRepository;
import mycode.onlineshopspring.orders.dto.OrdersListResponse;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public OrdersListResponse findAllOrders(int page, int size){
        Pageable pageable = PageRequest.of(Math.max(page,0), Math.max(size,1), Sort.by("id"));
        Page<Orders>ordersPage=ordersRepository.findAll(pageable);
        List<OrdersResponse>ordersResponseList=mapper.mapOrdersListToResponseList(ordersPage.getContent());
        return new OrdersListResponse(
                ordersResponseList,
                ordersPage.getTotalElements(),
                ordersPage.getTotalPages(),
                ordersPage.getNumber(),
                ordersPage.getSize());
    }
}
