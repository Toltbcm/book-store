package org.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.bookstore.constant.ColumnName;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.User.ID)
    private Long id;

    @Column(name = ColumnName.User.EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = ColumnName.User.PASSWORD, nullable = false)
    private String password;

    @Column(name = ColumnName.User.FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = ColumnName.User.LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = ColumnName.User.SHIPPING_ADDRESS, nullable = false)
    private String shippingAddress;
}
