package org.bookstore.repository;

import java.util.Optional;
import org.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("FROM ShoppingCart c JOIN FETCH c.cartItems i JOIN FETCH i.book "
            + "JOIN FETCH c.user u WHERE u.email = :email")
    Optional<ShoppingCart> findByUserEmailWithItemsWithBookAndUser(@Param("email") String email);

    @Query("FROM ShoppingCart c JOIN c.user u WHERE u.email = :email")
    Optional<ShoppingCart> findByUserEmail(@Param("email") String email);
}
