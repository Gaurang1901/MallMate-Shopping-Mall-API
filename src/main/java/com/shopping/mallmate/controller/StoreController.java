package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.store.GetAllStoresResponse;
import com.shopping.mallmate.dto.store.StoreCreateUpdateRequest;
import com.shopping.mallmate.dto.store.StoreModal;
import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.repository.StoreRepository;
import com.shopping.mallmate.service.CategoryService;
import com.shopping.mallmate.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shopping.mallmate.dto.store.StoreModal.fromEntity;

@RestController
@RequestMapping("/admin/v1/")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/store")
    public ResponseEntity<ApiResponse> createStore(@RequestBody StoreCreateUpdateRequest store) {
        Store createdStore = storeService.createStore(store);

        ApiResponse response = new ApiResponse();
        response.setMessage("Store created successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/store/{id}")
    public ResponseEntity<ApiResponse> createStore(@RequestBody StoreCreateUpdateRequest store, @PathVariable String id) {
        Store createdStore = storeService.updateStore(store, id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Store updated successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/store/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable String id) {
        storeService.deleteStore(id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Store deleted successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stores")
    public ResponseEntity<GetAllStoresResponse> getAllStores() {
        List<Store> stores = storeService.findAllStores();
        List<StoreModal> storeModals = stores.stream().map(StoreModal::fromEntity).toList();
        GetAllStoresResponse response = new GetAllStoresResponse();
        response.setStores(storeModals);
        response.setMessage("Stores fetched successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable String id) {
        Store store = storeService.findStoreById(id);
        return new ResponseEntity<>(store, HttpStatus.OK);

    }

    @GetMapping("/store")
    public ResponseEntity<List<Store>> searchStore(@RequestParam String keyword) {
        List<Store> stores = storeService.searchStore(keyword);

        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

}
