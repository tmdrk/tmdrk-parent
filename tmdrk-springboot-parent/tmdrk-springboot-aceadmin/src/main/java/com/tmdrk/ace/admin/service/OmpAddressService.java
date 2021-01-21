package com.tmdrk.ace.admin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OmpAddressService
 *
 * @author Jie.Zhou
 * @date 2021/1/19 14:37
 */
public interface OmpAddressService {
    void parseAddress();

    void generateAddress(MultipartFile file);

    void download();

    void selectCursor();
}
