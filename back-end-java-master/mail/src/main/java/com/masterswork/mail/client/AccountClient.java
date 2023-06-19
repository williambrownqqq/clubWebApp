package com.masterswork.mail.client;

import com.masterswork.mail.client.dto.account.AppUserResponseDTO;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CollectionFormat(feign.CollectionFormat.CSV)
@FeignClient(name = "account-client", url = "${client.account-uri}")
public interface AccountClient {

    @GetMapping("/app-users/current")
    AppUserResponseDTO getCurrentUser();

    @GetMapping("/app-users/{id}")
    AppUserResponseDTO getUserById(@PathVariable("id") Long id);
}
