package com.masterswork.account.service.mapper;

import com.masterswork.account.api.auth.dto.SignUpRequestDTO;
import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.api.dto.account.AccountUpdateDTO;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.Role;
import com.masterswork.account.service.mapper.qualifier.PasswordEncoded;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = AccountMapperUtils.class)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedBy = PasswordEncoded.class)
    Account createFrom(SignUpRequestDTO source);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget Account target, AccountUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget Account target, AccountUpdateDTO source);

    @Mapping(target = "appUserRef", expression = "java(mapAppUserReference(account))")
    AccountResponseDTO toDto(Account account);

    List<AccountResponseDTO> toDto(Collection<Account> account);

    default String mapRoleName(Role role) {
        return role.getName();
    }
    default String mapAppUserReference(Account account) {
        return Optional.ofNullable(account.getUser())
                .map(user -> "/app-users/" + user.getId())
                .orElse(null);
    }

}
