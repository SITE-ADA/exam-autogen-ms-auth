package az.edu.ada.msauth.service;

import az.edu.ada.msauth.model.entities.Address;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AddressService {
    public Address createAddress(Address address);
    public List<Address> getAllAddresses();
    public Optional<Address> getAddressById(Long id);
    public Address updateAddress(Long id, Address updatedAddress);
    public Address patchAddress(Long id, Map<String, Object> updates);
    public void deleteAddress(Long id);
}
