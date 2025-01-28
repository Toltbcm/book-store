package org.bookstore.repository;

import java.util.Optional;
import org.bookstore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o "
            + "JOIN FETCH o.orderItems i JOIN FETCH i.book "
            + "JOIN FETCH o.user u WHERE u.id = :id")
    Page<Order> getAllByUserIdWithItemsWithBookAndUser(@Param("id") Long userId, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o "
            + "JOIN FETCH o.orderItems i JOIN FETCH i.book "
            + "JOIN FETCH o.user u WHERE o.id = :id")
    Optional<Order> findByIdWithItemsWithBookAndUser(@Param("id") Long id);
}
