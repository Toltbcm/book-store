package org.bookstore.repository.item;

import java.util.Optional;
import org.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("FROM CartItem i JOIN FETCH i.book JOIN FETCH i.shoppingCart c "
            + "JOIN FETCH c.user WHERE i.id = :id")
    Optional<CartItem> fetchFullById(@Param("id") Long id);
}
