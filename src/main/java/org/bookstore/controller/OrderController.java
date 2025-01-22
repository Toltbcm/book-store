package org.bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Endpoints for order management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

}
