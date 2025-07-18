package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.product.ProductCreateUpdateRequest;
import com.shopping.mallmate.dto.product.ProductFilterRequest;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.repository.ProductCategoryRepository;
import com.shopping.mallmate.repository.ProductRepository;
import com.shopping.mallmate.repository.StoreRepository;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> findAllProductsWithFilters(ProductFilterRequest filterRequest) {
        PageRequest pageable = PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize());
        Specification<Product> specification = Specification.where(null);

        if (filterRequest.getName() != null && !filterRequest.getName().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%")
            );
        }
        if (filterRequest.getCategoryId() != null && !filterRequest.getCategoryId().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("productCategory").get("id"), filterRequest.getCategoryId())
            );
        }
        if (filterRequest.getBrandName() != null && !filterRequest.getBrandName().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("brandName")), "%" + filterRequest.getBrandName().toLowerCase() + "%")
            );
        }
        if (filterRequest.getStoreId() != null && !filterRequest.getStoreId().isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("store").get("id"), filterRequest.getStoreId())
            );
        }
        if (filterRequest.getMinPrice() != null) {
            specification = specification.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("price"), filterRequest.getMinPrice())
            );
        }
        if (filterRequest.getMaxPrice() != null) {
            specification = specification.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("price"), filterRequest.getMaxPrice())
            );
        }
        if (filterRequest.getIsAvailable() != null) {
            if (filterRequest.getIsAvailable()) {
                specification = specification.and((root, query, cb) ->
                        cb.greaterThan(root.get("quantity"), 0)
                );
            } else {
                specification = specification.and((root, query, cb) ->
                        cb.equal(root.get("quantity"), 0)
                );
            }
        }
        // Note: Filtering by rating would require a join/subquery for average rating, which is not trivial in Specification API.
        // For now, we skip rating filter unless you want to implement a custom query.

        // Sorting
        if (filterRequest.getSortBy() != null && !filterRequest.getSortBy().isEmpty()) {
            String sortBy = filterRequest.getSortBy();
            String sortOrder = filterRequest.getSortOrder() != null ? filterRequest.getSortOrder() : "asc";
            if (sortOrder.equalsIgnoreCase("desc")) {
                pageable = PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize(), org.springframework.data.domain.Sort.by(sortBy).descending());
            } else {
                pageable = PageRequest.of(filterRequest.getPageNumber(), filterRequest.getPageSize(), org.springframework.data.domain.Sort.by(sortBy).ascending());
            }
        }

        return productRepository.findAll(specification, pageable);
    }

    public Product findProductById(String id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return product;
    }

    public Product createProduct(ProductCreateUpdateRequest product) {
        Product createdProduct = new Product();
        ProductCategory category = productCategoryRepository.findCategoryById(product.getCategoryId());
        Store store = storeRepository.findStoreById(product.getStoreId());
        createdProduct.setName(product.getName());
        createdProduct.setDescription(product.getDescription());
        createdProduct.setPrice(product.getPrice());
        createdProduct.setQuantity(product.getQuantity());
        createdProduct.setImages(product.getImages());
        createdProduct.setProductCategory(category);
        createdProduct.setStore(store);
        createdProduct.setBrandName(product.getBrandName());
        createdProduct.setCreatedAt(new Date());
        createdProduct.setUpdatedAt(new Date());
        return productRepository.save(createdProduct);
    }

    public Product updateProduct(String id, ProductCreateUpdateRequest product) {
        Product existingProduct = productRepository.findProductById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }

        if (product.getName() != null && !product.getName().isEmpty()) {
            existingProduct.setName(product.getName());
        }
        if (product.getDescription() != null && !product.getDescription().isEmpty()) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() > 0) {
            existingProduct.setPrice(product.getPrice());
        }
        if (!product.getBrandName().isEmpty()) {
            existingProduct.setBrandName(product.getBrandName());
        }
        if (product.getQuantity() > 0) {
            existingProduct.setQuantity(product.getQuantity());
        }
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            existingProduct.setImages(product.getImages());
        }
        if (product.getCategoryId() != null) {
            ProductCategory category = productCategoryRepository.findCategoryById(product.getCategoryId());
            if (category == null) {
                throw new RuntimeException("Category not found");
            }
            existingProduct.setProductCategory(category);
        }
        if (product.getStoreId() != null) {
            Store store = storeRepository.findStoreById(product.getStoreId());
            if (store == null) {
                throw new RuntimeException("Store not found");
            }
            existingProduct.setStore(store);
        }
        existingProduct.setUpdatedAt(new Date());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        productRepository.delete(product);
    }

    public List<Product> searchProduct(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();
        }
        return productRepository.searchProduct(keyword.trim());
    }

}
