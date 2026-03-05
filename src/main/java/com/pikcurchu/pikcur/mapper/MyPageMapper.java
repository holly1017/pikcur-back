package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.response.MyPageInfoDto;
import com.pikcurchu.pikcur.dto.response.ResMyStoreDto;
import com.pikcurchu.pikcur.vo.Member;
import com.pikcurchu.pikcur.vo.Store;

import java.time.LocalDate;

public interface MyPageMapper {

    MyPageInfoDto selectMyInfoById(Integer memberNo);

    int updateMyInfo(Member member);

    ResMyStoreDto selectMyStore(Integer memberNo);

    int updateMyStore(Store store);

    int updatePassword(Integer memberNo, String password);

    int updateStoreProfilePath(Integer storeId, String uploadedPath);
}
