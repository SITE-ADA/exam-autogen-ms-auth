package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.Address;
import az.edu.ada.msauth.repository.AddressRepository;
import az.edu.ada.msauth.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Address updateAddress(Long id, Address updatedAddress) {
        Optional<Address> existingAddressOptional = addressRepository.findById(id);

        if (existingAddressOptional.isPresent()) {
            Address existingAddress = existingAddressOptional.get();

            updatedAddress.setCreatedAt(existingAddress.getCreatedAt());

            updatedAddress.setId(id);

            return addressRepository.save(updatedAddress);
        } else {
            return null;
        }
    }

    public Address patchAddress(Long id, Map<String, Object> updates) {
        Optional<Address> existingAddressOptional = addressRepository.findById(id);

        if (existingAddressOptional.isPresent()) {
            Address existingAddress = existingAddressOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "country":
                        existingAddress.setCountry((String) value);
                        break;
                    case "city":
                        existingAddress.setCity((String) value);
                        break;
                    default:
                        break;
                }
            });

            existingAddress.setId(id);

            return addressRepository.save(existingAddress);
        } else {
            return null;
        }
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
