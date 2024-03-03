package az.edu.ada.msauth.config;

import az.edu.ada.msauth.mapper.InstitutionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public InstitutionMapper institutionMapper() {
        return Mappers.getMapper(InstitutionMapper.class);
    }
}