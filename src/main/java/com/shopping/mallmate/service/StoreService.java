package com.shopping.mallmate.service;


import com.shopping.mallmate.dto.store.StoreCreateUpdateRequest;
import com.shopping.mallmate.entity.Category;
import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.repository.CategoryRepository;
import com.shopping.mallmate.repository.StoreRepository;
import com.shopping.mallmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    public Store findStoreById(String id) {
        Store store = storeRepository.findStoreById(id);
        if (store == null) {
            throw new RuntimeException("Store not found");
        }
        return store;
    }

    public Store createStore(StoreCreateUpdateRequest store) {
        Store createdStore = new Store();
        User user = userRepository.findUserById(store.getUserId());
        Category category = categoryRepository.findCategoryById(store.getCategoryId());
        createdStore.setUser(user);
        createdStore.setCategory(category);
        createdStore.setName(store.getName());
        createdStore.setDescription(store.getDescription());
        createdStore.setPhoneNumber(store.getPhoneNumber());
        createdStore.setInstagramLink(store.getInstagramLink());
        createdStore.setFacebookLink(store.getFacebookLink());
        createdStore.setTwitterLink(store.getTwitterLink());
        createdStore.setImage(store.getImages());
        return storeRepository.save(createdStore);
    }

    public Store updateStore(StoreCreateUpdateRequest store, String id) {
        Store existingStore = storeRepository.findStoreById(id);
        if (store.getName() != null && !store.getName().isEmpty()) {
            existingStore.setName(store.getName());
        }
        if (store.getDescription() != null && !store.getDescription().isEmpty()) {
            existingStore.setDescription(store.getDescription());
        }
        if (store.getImages() != null && !store.getImages().isEmpty()) {
            existingStore.setImage(store.getImages());
        }
        if (store.getUserId() != null) {
            User user = userRepository.findUserById(store.getUserId());
            existingStore.setUser(user);
        }
        if (store.getCategoryId() != null) {
            Category category = categoryRepository.findCategoryById(store.getCategoryId());
            existingStore.setCategory(category);
        }
        if (store.getPhoneNumber() != null && !store.getPhoneNumber().isEmpty()) {
            existingStore.setPhoneNumber(store.getPhoneNumber());
        }
        if (store.getInstagramLink() != null && !store.getInstagramLink().isEmpty()) {
            existingStore.setInstagramLink(store.getInstagramLink());
        }
        if (store.getFacebookLink() != null && !store.getFacebookLink().isEmpty()) {
            existingStore.setFacebookLink(store.getFacebookLink());
        }
        if (store.getTwitterLink() != null && !store.getTwitterLink().isEmpty()) {
            existingStore.setTwitterLink(store.getTwitterLink());
        }
        return storeRepository.save(existingStore);
    }

    public void deleteStore(String id) {
        Store store = storeRepository.findStoreById(id);
        if (store == null) {
            throw new RuntimeException("Store not found");
        }
        storeRepository.delete(store);
    }

    public List<Store> searchStore(String keyword) {
        return storeRepository.searchStore(keyword);
    }

}
