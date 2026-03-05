package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqShippingDto;
import com.pikcurchu.pikcur.dto.response.ResTransactionDetailDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.TransactionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionsMapper transactionMapper;

    public ResTransactionDetailDto selectTransactionInfo(Integer transactionId) {
        return transactionMapper.findTransactionInfoById(transactionId);
    }

    @Transactional
    public void insertShippingInfo(Integer transactionId, ReqShippingDto reqShippingDto) {
        reqShippingDto.setTransactionId(transactionId);
        int result = transactionMapper.insertShippingInfo(reqShippingDto);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
    }

    @Transactional
    public void confirmPurchase(Integer transactionId) {
        int result = transactionMapper.updateTransactionStatus(transactionId);
        if (result == 0) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
    }
}
