package org.bookstore.repository;

import org.bookstore.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("FROM OrderItem i JOIN FETCH i.book JOIN i.order o WHERE o.id = :orderId")
    Page<OrderItem> getAllByOrderIdWithBook(@Param("orderId") Long orderId, Pageable pageable);
}
