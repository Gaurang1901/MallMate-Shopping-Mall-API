package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.Store;
import com.shopping.mallmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

    Store findStoreById(String id);

    Store findStoreByUser(User user);

    @Query("SELECT s FROM Store s WHERE s.name LIKE %:keyword% OR s.description LIKE %:keyword% OR s.category.name LIKE %:keyword%")
    List<Store> searchStore(String keyword);


}
