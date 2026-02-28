package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ApiResponse;
import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqSigninDto;
import com.pikcurchu.pikcur.dto.request.ReqSignupDto;
import com.pikcurchu.pikcur.dto.response.ResSigninDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.AuthMapper;
import com.pikcurchu.pikcur.util.JwtUtil;
import com.pikcurchu.pikcur.util.PasswordUtil;
import com.pikcurchu.pikcur.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthMapper authMapper;

    @Transactional(readOnly = true)
    public ResSigninDto signin(ReqSigninDto request) {
        Member member = authMapper.authById(request.getId());

        if (member == null) {
            throw new BusinessException(ResponseCode.USER_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }

        String statusNo = authMapper.findStatusNo(request.getId());
        if (!"01".equals(statusNo)) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        if (!PasswordUtil.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(ResponseCode.INVALID_PASSWORD, HttpStatus.UNAUTHORIZED);
        }

        String token = JwtUtil.generateToken(member.getMemberNo());
        return new ResSigninDto(
                token,
                member.getId(),
                member.getName(),
                member.getAuthority());
    }

    @Transactional
    public int signup(ReqSignupDto request) {
        // 중복 아이디 체크
        if (authMapper.countById(request.getId()) > 0) {
            throw new BusinessException(ResponseCode.DUPLICATE);
        }

        Member member = new Member();
        member.setId(request.getId());
        member.setPassword(PasswordUtil.encode(request.getPassword()));
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setBirth(request.getBirth());
        member.setGender(request.getGender());

        int result = authMapper.insertMember(member);

        if (result > 0 && member.getMemberNo() != null) {
            String storeName = member.getName() + "님의 상점";
            authMapper.insertStore(member.getMemberNo(), storeName);
        }

        return result;
    }

    public String findIdByEmail(String email) {
        String id = authMapper.findIdByEmail(email);

        if (id == null || id.isEmpty()) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }

        return id;
    }

    public boolean updateMemberToWithdrawal(Integer memberNo) {
        int updated = authMapper.updateMemberToWithdrawal(memberNo);
        return updated > 0;
    }

    public Integer countById(String id) {
        return authMapper.countById(id);
    }

    public Integer updatePasswordStatusLogin(Integer memberNo, String password) {
        String encodedPassword = PasswordUtil.encode(password);
        int result = authMapper.updatePasswordStatusLogin(memberNo, encodedPassword);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        return result;
    }

    public Integer updatePasswordStatusUnLogin(String id, String password) {
        String encodedPassword = PasswordUtil.encode(password);
        int result = authMapper.updatePasswordStatusUnLogin(id, encodedPassword);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        return result;
    }

    public int insertStore(Integer memberNo, String storeName) {
        return authMapper.insertStore(memberNo, storeName);
    }
}
