package mycode.onlineshopspring.orders.service;

import jakarta.transaction.Transactional;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.exceptions.CustomerDoesntExistException;
import mycode.onlineshopspring.exceptions.ProductDoesntExistException;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orderDetails.dto.OrderDetailsDto;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import mycode.onlineshopspring.orders.dto.OrdersDto;
import mycode.onlineshopspring.orders.dto.OrdersResponse;
import mycode.onlineshopspring.orders.models.Orders;
import mycode.onlineshopspring.orders.repository.OrdersRepository;
import mycode.onlineshopspring.products.models.Products;
import mycode.onlineshopspring.products.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
    public OrdersResponse createOrder(Long customerId, OrdersDto dto) throws CustomerDoesntExistException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerDoesntExistException::new);
        Orders order = mapper.mapOrdersDtoToOrders(dto);
        order.setCustomer(customer);

        Set<OrderDetailsDto> detailDtos = dto.orderDetailsSet();
        if (detailDtos != null && !detailDtos.isEmpty()) {
            attachOrderDetails(order, detailDtos);
        }

        Orders savedOrder = repository.save(order);
        return mapper.mapOrdersToOrdersResponse(savedOrder);
    }

    private void attachOrderDetails(Orders order, Set<OrderDetailsDto> detailDtos) {
        Set<Long> productIds = detailDtos.stream().map(OrderDetailsDto::productId).collect(Collectors.toSet());
        List<Products> products = productsRepository.findAllById(productIds);
        Map<Long, Products> productsById = products.stream().collect(Collectors.toMap(Products::getId, p -> p));

        if (productsById.size() != productIds.size()) {
            throw new ProductDoesntExistException();
        }

        Set<OrderDetails> detailEntities = new HashSet<>();
        for (OrderDetailsDto detailDto : detailDtos) {
            OrderDetails detail = mapper.mapOrderDetailsDtoToOrderDetails(detailDto);
            Products product = productsById.get(detailDto.productId());
            if (product == null) {
                throw new ProductDoesntExistException();
            }
            detail.setProduct(product);
            detail.setOrder(order);
            detailEntities.add(detail);
        }

        order.getOrderDetailsSet().addAll(detailEntities);
    }
}
