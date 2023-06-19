package com.masterswork.process.client;

import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@CollectionFormat(feign.CollectionFormat.CSV)
@FeignClient(name = "organization-client", url = "${client.organization-uri}")
public interface OrganizationClient {

    @GetMapping(path = "/organization/participants", produces = "application/json")
    Set<Long> getParticipantsForOrganizations(@RequestParam("organizationId") Set<Long> organizationId);

}