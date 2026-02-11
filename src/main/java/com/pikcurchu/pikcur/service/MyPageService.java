package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.dto.response.MyPageInfoDto;
import com.pikcurchu.pikcur.dto.response.ResMyStoreDto;
import com.pikcurchu.pikcur.mapper.MyPageMapper;
import com.pikcurchu.pikcur.util.PasswordUtil;
import com.pikcurchu.pikcur.vo.Member;
import com.pikcurchu.pikcur.vo.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class MyPageService {
    private final MyPageMapper myPageMapper;
    private final FileService fileService;

    public MyPageService(MyPageMapper myPageMapper, FileService fileService) {
        this.myPageMapper = myPageMapper;
        this.fileService = fileService;
    }

    public MyPageInfoDto selectMyInfoById(Integer memberNo) {

        return myPageMapper.selectMyInfoById(memberNo);
    }

    public int updateMyInfo(Member member) {
        return myPageMapper.updateMyInfo(member);
    }

    public ResMyStoreDto selectMyStore(Integer memberNo) {
        return myPageMapper.selectMyStore(memberNo);
    }

    public int updateMyStore(Store store) {
        return myPageMapper.updateMyStore(store);
    }

    public Integer updatePassword(Integer memberNo, String password) {
        String encodedPassword = PasswordUtil.encode(password);

        return myPageMapper.updatePassword(memberNo, encodedPassword);
    }

    @Transactional
    public void updateStoreProfile(Integer storeId, MultipartFile image) {
        String uploadedPath = fileService.profileUploadFile(image);
        myPageMapper.updateStoreProfilePath(storeId, uploadedPath);
    }
}
