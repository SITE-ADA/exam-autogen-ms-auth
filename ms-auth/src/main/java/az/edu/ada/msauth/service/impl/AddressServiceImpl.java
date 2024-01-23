package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.Address;
import az.edu.ada.msauth.repository.AddressRepository;
import az.edu.ada.msauth.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address updateAddress(Long id, Address updatedAddress) {
        Optional<Address> existingAddressOptional = addressRepository.findById(id);

        if (existingAddressOptional.isPresent()) {
            updatedAddress.setId(id);
            return addressRepository.save(updatedAddress);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Address patchAddress(Long id, Map<String, Object> updates) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            return null;
        }

        Address address = optionalAddress.get();
        applyPatchToAddress(address, updates);
        addressRepository.save(address);
        return address;
    }

    private void applyPatchToAddress(Address address, Map<String, Object> updates) {
        Class<?> clazz = address.getClass();
        updates.forEach((key, value) -> {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(address, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception, possibly logging a warning or throwing a custom exception
            }
        });
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
