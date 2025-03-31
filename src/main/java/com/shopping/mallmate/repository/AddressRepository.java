package com.shopping.mallmate.repository;

import com.shopping.mallmate.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {

    Address findAddressById(String id);

    Address findAddressByPincode(String pincode);
}
