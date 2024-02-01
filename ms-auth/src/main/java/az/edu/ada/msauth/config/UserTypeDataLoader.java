package az.edu.ada.msauth.config;

import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.model.entities.UserType;
import az.edu.ada.msauth.model.enums.EUserType;
import az.edu.ada.msauth.repository.UserTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserTypeDataLoader implements CommandLineRunner {
    private final UserTypeRepository userTypeRepository;

    public UserTypeDataLoader(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (EUserType eUserType : EUserType.values()) {
            UserType userType = userTypeRepository.findByName(eUserType);
            if (userType == null) {
                userType = UserType.builder()
                        .name(eUserType)
                        .build();
                userTypeRepository.save(userType);
            }
        }
    }
}
