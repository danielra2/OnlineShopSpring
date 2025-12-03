package mycode.onlineshopspring.orderDetails.service;

import mycode.onlineshopspring.common.pagination.PaginationUtils;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsListResponse;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsResponse;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import mycode.onlineshopspring.orderDetails.repository.OrderDetailsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static mycode.onlineshopspring.common.pagination.PaginationUtils.fetchPage;

@Service
public class OrderDetailsQuerryServiceImpl implements OrderDetailsQuerryService{
    private final OrderDetailsRepository orderDetailsRepository;
    private final OnlineShopMapper mapper;
    public OrderDetailsQuerryServiceImpl(OrderDetailsRepository orderDetailsRepository,OnlineShopMapper mapper){
        this.orderDetailsRepository=orderDetailsRepository;
        this.mapper=mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public OrderDetailsListResponse findAllOrderDetails(int page, int size){
        Pageable pageable = PaginationUtils.sanitize(page, size, Sort.by("id"));
        Page<OrderDetails>orderDetailsPage=fetchPage(orderDetailsRepository::findAll,pageable);
        List<OrderDetailsResponse>orderDetailsResponseList=mapper.mapOrderDetailsListToResponseList(orderDetailsPage.getContent());
        return new OrderDetailsListResponse(
                orderDetailsResponseList,
                orderDetailsPage.getTotalElements(),
                orderDetailsPage.getTotalPages(),
                orderDetailsPage.getNumber(),
                orderDetailsPage.getSize());
    }
}
