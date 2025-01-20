package org.bookstore.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bookstore.model.Category;
import org.bookstore.service.CategoryService;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MapperUtil {

    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("setCategories")
    public Set<Category> setCategories(List<Long> ids) {
        Set<Category> categories = new HashSet<>();
        ids.forEach(id -> categories.add(categoryService.getCategory(id)));
        return categories;
    }
}
