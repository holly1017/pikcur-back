package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqAccountDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.AccountMapper;
import com.pikcurchu.pikcur.vo.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    private final AccountMapper accountMapper;

    public AccountService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Transactional
    public Integer insertAccount(ReqAccountDto req, Integer memberNo) {
        int isDefaultCount = selectAccountIsDefault(memberNo);
        String isDefault = (isDefaultCount > 0) ? "N" : "Y";

        int result = accountMapper.insertAccount(req, memberNo, isDefault);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);

        return result;
    }

    public Integer selectAccountIsDefault(Integer memberNo) {
        return accountMapper.selectAccountIsDefault(memberNo);
    }

    public List<Account> selectAccountList(Integer memberNo) {
        return accountMapper.selectAccountList(memberNo);
    }

    @Transactional
    public Integer updateAccount(ReqAccountDto req, Integer memberNo) {
        int result = accountMapper.updateAccount(req, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }

    @Transactional
    public Integer deleteAccount(Integer accountId, Integer memberNo) {
        int result = accountMapper.deleteAccount(accountId, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);

        accountMapper.updateDefaultAccountAfterDelete(accountId, memberNo);
        return result;
    }

    @Transactional
    public Integer updateDefaultAccount(int accountId, int memberNo) {
        accountMapper.updateDefaultAccountN(memberNo);
        int result = accountMapper.updateDefaultAccountY(accountId, memberNo);

        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }
}
