package com.dailydreams.dailydreams.service.order;

import com.dailydreams.dailydreams.Dtos.OrderDto;
import com.dailydreams.dailydreams.enums.OrderStatus;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Cart;
import com.dailydreams.dailydreams.model.Order;
import com.dailydreams.dailydreams.model.OrderItem;
import com.dailydreams.dailydreams.model.Product;
import com.dailydreams.dailydreams.repository.CartRepository;
import com.dailydreams.dailydreams.repository.OrderRepository;
import com.dailydreams.dailydreams.repository.ProductRepository;
import com.dailydreams.dailydreams.service.cart.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart   = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;

    }


    private Order createOrder(Cart cart) {
        Order order = new Order();

        //set the user
        order.setUser(cart.getUser());

        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());


        return order;

    }






    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getCartItems().stream()
                .map(cartItem ->
                {
                        Product product = cartItem.getProduct();
                        product.setInventory(product.getInventory()-cartItem.getQuantity());
                        productRepository.save(product);
                        return new OrderItem(
                                order,
                                product,
                                cartItem.getQuantity(),
                                cartItem.getUnitPrice()
                        );

                }).toList();
    }


    @Override
    public List<OrderDto> getUserOrders(Long userID) {

        List<Order> orders= orderRepository.findByUserUserId(userID);

        return orders.stream().map(this::covertToOrderDto).toList();

    }





    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemsList) {

        return orderItemsList.stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }




    @Override
    public OrderDto getOrder(Long orderID) {
        return orderRepository.findById(orderID).map(this::covertToOrderDto)
                .orElseThrow(()->new ResourceNotFoundException("No Order found"));
    }


    @Override
    public OrderDto covertToOrderDto(Order order) {
    return modelMapper.map(order, OrderDto.class);
    }
}
