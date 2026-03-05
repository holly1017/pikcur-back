package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.response.MyPageInfoDto;
import com.pikcurchu.pikcur.dto.response.ResMyStoreDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.MyPageMapper;
import com.pikcurchu.pikcur.util.PasswordUtil;
import com.pikcurchu.pikcur.vo.Member;
import com.pikcurchu.pikcur.vo.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MyPageService {
    private final MyPageMapper myPageMapper;
    private final FileService fileService;

    public MyPageService(MyPageMapper myPageMapper, FileService fileService) {
        this.myPageMapper = myPageMapper;
        this.fileService = fileService;
    }

    public MyPageInfoDto selectMyInfoById(Integer memberNo) {
        MyPageInfoDto info = myPageMapper.selectMyInfoById(memberNo);
        if (info == null)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return info;
    }

    @Transactional
    public int updateMyInfo(Member member) {
        int result = myPageMapper.updateMyInfo(member);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }

    public ResMyStoreDto selectMyStore(Integer memberNo) {
        ResMyStoreDto store = myPageMapper.selectMyStore(memberNo);
        if (store == null)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return store;
    }

    @Transactional
    public int updateMyStore(Store store) {
        int result = myPageMapper.updateMyStore(store);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }

    @Transactional
    public Integer updatePassword(Integer memberNo, String password) {
        String encodedPassword = PasswordUtil.encode(password);
        int result = myPageMapper.updatePassword(memberNo, encodedPassword);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }

    @Transactional
    public void updateStoreProfile(Integer storeId, MultipartFile image) {
        String uploadedPath = fileService.profileUploadFile(image);
        int result = myPageMapper.updateStoreProfilePath(storeId, uploadedPath);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
    }
}
