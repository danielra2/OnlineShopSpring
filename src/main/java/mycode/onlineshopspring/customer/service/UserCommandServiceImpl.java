package mycode.onlineshopspring.customer.service;
import mycode.onlineshopspring.customer.dto.CustomerDto;
import mycode.onlineshopspring.customer.dto.CustomerResponse;
import mycode.onlineshopspring.customer.models.Customer;
import mycode.onlineshopspring.customer.repository.CustomerRepository;
import mycode.onlineshopspring.mappers.OnlineShopMapper;
import mycode.onlineshopspring.orders.models.Orders;
import mycode.onlineshopspring.orderDetails.models.OrderDetails;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Set;

@Service
public class UserCommandServiceImpl implements UserCommandService{
    private final CustomerRepository repository;
    private final OnlineShopMapper mapper;

    public UserCommandServiceImpl(CustomerRepository repository, OnlineShopMapper mapper){
        this.repository=repository;
        this.mapper=mapper;
    }
    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerDto dto){
        Customer customer=mapper.mapCustomerDtoToCustomer(dto);
        Set<Orders>ordersSet=mapper.mapOrdersDtoSetToEntitySet(dto.orderSet());

        ordersSet.forEach(order->{
            order.setCustomer(customer);
            Set<OrderDetails>detailsSet=order.getOrderDetailsSet();
            detailsSet.forEach(detail->detail.setOrder(order));
            customer.getOrderSet().add(order);
        });

        Customer savedCustomer=repository.save(customer);
        return mapper.mapCustomerToCustomerResponse(savedCustomer);
    }

//    @Transactional
//    @Override
//    public CustomerResponse createCustomer(CustomerDto dto){
//        Customer customer=mapper.mapCustomerDtoToCustomer(dto);
//        Set<Orders>ordersSet=mapper.mapOrdersDtoSetToEntitySet(dto.orderSet());
//
//        ordersSet.forEach(order->{
//            // 1. Stabileste relatia Customer <-> Order (folosind metoda helper)
//            customer.addOrder(order);
//
//            // Găsim setul DTO corespunzător comenzii curente
//            Set<mycode.onlineshopspring.orderDetails.dto.OrderDetailsDto>dtoDetailsSet=dto.orderSet().stream().filter(o->o.orderEmail().equals(order.getOrderEmail())).findFirst().get().orderDetailsSet();
//
//            Set<OrderDetails>detailsSet=mapper.mapOrderDetailsDtoSetToEntitySet(dtoDetailsSet);
//            detailsSet.forEach(detail->{
//                // 2. Stabileste relatia Order <-> OrderDetails (folosind metoda helper)
//                order.addOrderDetails(detail);
//                // 3. Stabileste relatia OrderDetails -> Product (fetch din DB)
//                Long productIdFromDto=dtoDetailsSet.stream()
//                        .filter(d->d.sku().equals(detail.getSku()))
//                        .findFirst()
//                        .orElseThrow(ProductDoesntExistException::new)
//                        .productId();
//                detail.setProduct(productsRepository.findById(productIdFromDto)
//                        .orElseThrow(ProductDoesntExistException::new));
//            });
//        });
//
//        Customer savedCustomer=repository.save(customer);
//        return mapper.mapCustomerToCustomerResponse(savedCustomer);
//    }
}
