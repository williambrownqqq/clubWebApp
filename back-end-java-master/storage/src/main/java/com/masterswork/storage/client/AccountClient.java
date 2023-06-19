package com.masterswork.storage.client;

import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@CollectionFormat(feign.CollectionFormat.CSV)
@FeignClient(name = "account-client", url = "${client.account-uri}")
public interface AccountClient {

    @GetMapping(path = "/app-users/username", produces = "application/json")
    Set<String> getUsernamesForAppUsers(@RequestParam("userId") Set<Long> userId);

}
