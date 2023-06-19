package com.masterswork.mail.client;

import com.masterswork.mail.client.dto.storage.StoredFileDTO;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CollectionFormat(feign.CollectionFormat.CSV)
@FeignClient(name = "storage-client", url = "${client.storage-uri}")
public interface StorageClient {

    @GetMapping("/files/current")
    Page<StoredFileDTO> getFiles(Pageable page);

    @GetMapping("/files/{id}")
    Resource loadById(@PathVariable("id") Long ids);
}
