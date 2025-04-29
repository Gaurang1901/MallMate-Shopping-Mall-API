package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.product.ProductCreateUpdateRequest;
import com.shopping.mallmate.dto.productCategory.ProductCategoryCreateUpdateRequest;
import com.shopping.mallmate.entity.Product;
import com.shopping.mallmate.entity.ProductCategory;
import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.repository.ProductCategoryRepository;
import com.shopping.mallmate.repository.ProductRepository;
import com.shopping.mallmate.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (product.getQuantity() > 0) {
            existingProduct.setQuantity(product.getQuantity());
        }
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            existingProduct.setImages(product.getImages());
        }
        if (product.getCategoryId() != null) {
            ProductCategory category = productCategoryRepository.findCategoryById(product.getCategoryId());
            existingProduct.setProductCategory(category);
        }
        if (product.getStoreId() != null) {
            Store store = storeRepository.findStoreById(product.getStoreId());
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
        return productRepository.searchProduct(keyword);
    }

}
