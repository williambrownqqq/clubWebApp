package com.masterswork.process.client;

import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;


@CollectionFormat(feign.CollectionFormat.CSV)
@FeignClient(name = "storage-client", url = "${client.storage-uri}")
public interface StorageClient {

    @PostMapping("/access/grant")
    void grantFilesAccessByUsersIds(@RequestParam("fileIds") Set<Long> fileIds,
                                    @RequestParam("appUserIds") Set<Long> appUserIds,
                                    @RequestBody Map<String, String> fileAccessPermissionDTO);

}
