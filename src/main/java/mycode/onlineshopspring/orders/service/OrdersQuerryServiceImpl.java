package mycode.onlineshopspring.orders.service;

import mycode.onlineshopspring.common.pagination.PaginationUtils;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orders.dto.OrdersListResponse;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import mycode.onlineshopspring.orders.models.Orders;
import mycode.onlineshopspring.orders.repository.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static mycode.onlineshopspring.common.pagination.PaginationUtils.fetchPage;

@Service
public class OrdersQuerryServiceImpl implements OrdersQuerryService{
    private final OrdersRepository ordersRepository;
    private final OnlineShopMapper mapper;
    public OrdersQuerryServiceImpl(OrdersRepository ordersRepository,OnlineShopMapper mapper){
        this.ordersRepository=ordersRepository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public OrdersListResponse findAllOrders(int page, int size){
        Pageable pageable = PaginationUtils.sanitize(page, size, Sort.by("id"));
        Page<Orders>ordersPage=fetchPage(ordersRepository::findAll,pageable);
        List<OrdersResponse>ordersResponseList=mapper.mapOrdersListToResponseList(ordersPage.getContent());
        return new OrdersListResponse(
                ordersResponseList,
                ordersPage.getTotalElements(),
                ordersPage.getTotalPages(),
                ordersPage.getNumber(),
                ordersPage.getSize());
    }
}
