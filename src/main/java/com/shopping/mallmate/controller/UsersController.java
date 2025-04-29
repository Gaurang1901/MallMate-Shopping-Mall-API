package com.shopping.mallmate.controller;

import com.shopping.mallmate.dto.ApiResponse;
import com.shopping.mallmate.dto.user.address.AddressModal;
import com.shopping.mallmate.dto.user.address.GetAddressByPincodeModal;
import com.shopping.mallmate.dto.user.address.GetAddressByUserId;
import com.shopping.mallmate.entity.Address;
import com.shopping.mallmate.service.AddressService;
import com.shopping.mallmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressModal> addAddress(@RequestBody AddressModal addressModal) {
        Address address = addressService.addAddress(addressModal, addressModal.getUserId());
        return ResponseEntity.ok(AddressModal.fromEntity(address));
    }

    @PostMapping("/address/{addressId}/{userId}")
    public ResponseEntity<AddressModal> updateAddress(@RequestBody AddressModal addressModal,@PathVariable String userId,@PathVariable String addressId) {
        Address address = addressService.updateAddress(addressModal, addressId);
        return ResponseEntity.ok(AddressModal.fromEntity(address));
    }

    @DeleteMapping("/address/{addressId}/{userId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable String addressId, @PathVariable String userId) {
        addressService.deleteAddress(addressId);
        ApiResponse response = new ApiResponse();
        response.setMessage("Address deleted successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/addressByUserId/{userId}")
    public ResponseEntity<GetAddressByUserId> findAddressByUserId(@PathVariable String userId) {
        List<Address> addresses = addressService.findAddressByUserId(userId);
        GetAddressByUserId response = new GetAddressByUserId();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Addresses fetched successfully");
        response.setAddressModal(addresses.stream().map(AddressModal::fromEntity).toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/addressByPincode/{pincode}")
    public ResponseEntity<GetAddressByPincodeModal> findAddressByPinCode(@PathVariable String pincode) {
        Address addresses = addressService.findAddressByPincode(pincode);
        GetAddressByPincodeModal response = new GetAddressByPincodeModal();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Addresses fetched successfully");
        response.setAddressModal(AddressModal.fromEntity(addresses));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<GetAddressByPincodeModal> findAddressById(@PathVariable String id) {
        Address addresses = addressService.findAddressById(id);
        GetAddressByPincodeModal response = new GetAddressByPincodeModal();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Addresses fetched successfully");
        response.setAddressModal(AddressModal.fromEntity(addresses));
        return ResponseEntity.ok(response);
    }
}
