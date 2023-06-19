package com.masterswork.account.service.mapper;

import com.masterswork.account.api.auth.dto.SignUpRequestDTO;
import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.model.AppUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "id", ignore = true)
    AppUser createFrom(AppUserCreateDTO source);

    @Mapping(target = "id", ignore = true)
    AppUser createFrom(SignUpRequestDTO signUpRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget AppUser target, AppUserUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget AppUser target, AppUserUpdateDTO source);

    @Mapping(target = "accountRef", expression = "java(mapAccountReference(appUser))")
    @Mapping(target = "email", source = "account.email")
    AppUserResponseDTO toDto(AppUser appUser);

    List<AppUserResponseDTO> toDto(Collection<AppUser> appUsers);

    default String mapAccountReference(AppUser appUser) {
        return Optional.ofNullable(appUser.getAccount())
                .map(account -> "/account/" + account.getId())
                .orElse(null);
    }

}
