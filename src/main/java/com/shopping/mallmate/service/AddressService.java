package com.shopping.mallmate.service;

import com.shopping.mallmate.dto.user.address.AddressModal;
import com.shopping.mallmate.entity.Address;
import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.repository.AddressRepository;
import com.shopping.mallmate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address addAddress(AddressModal address, String userId) {
        User user = userRepository.findUserById(userId);
        Address savedAddress = new Address();
        savedAddress.setAddressLine1(address.getAddressLine1());
        savedAddress.setAddressLine2(address.getAddressLine2());
        savedAddress.setCity(address.getCity());
        savedAddress.setState(address.getState());
        savedAddress.setCountry(address.getCountry());
        savedAddress.setUser(user);
        savedAddress.setPincode(address.getPincode());
        return addressRepository.save(savedAddress);
    }

    public Address updateAddress(AddressModal address, String id) {
        Address existingAddress = addressRepository.findAddressById(id);
        existingAddress.setAddressLine1(address.getAddressLine1());
        existingAddress.setAddressLine2(address.getAddressLine2());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setPincode(address.getPincode());
        return addressRepository.save(existingAddress);
    }

    public void deleteAddress(String id) {
        Address address = addressRepository.findAddressById(id);
        addressRepository.delete(address);
    }

    public Address findAddressById(String id) {
        Address address = addressRepository.findAddressById(id);
        if (address == null) {
            throw new RuntimeException("Address not found");
        } else {
            return address;
        }
    }

    public Address findAddressByPincode(String pincode) {
        Address address = addressRepository.findAddressByPincode(pincode);
        if (address == null) {
            throw new RuntimeException("Address not found");
        } else {
            return address;
        }
    }

    public List<Address> findAddressByUserId(String userId) {
        List<Address> addresses = addressRepository.findAddressByUserId(userId);

        if (addresses == null || addresses.isEmpty()) {
            throw new RuntimeException("Address not found");
        }

        return addresses;
    }


}
