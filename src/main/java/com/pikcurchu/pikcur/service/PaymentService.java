package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqVerifyPaymentDto;
import com.pikcurchu.pikcur.dto.response.ResPaymentAddressDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.GoodsMapper;
import com.pikcurchu.pikcur.mapper.PaymentMapper;
import com.pikcurchu.pikcur.mapper.TransactionsMapper;
import com.pikcurchu.pikcur.vo.Payment;
import com.pikcurchu.pikcur.vo.Transactions;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final GoodsMapper goodsMapper;
    private final PaymentMapper paymentMapper;
    private final TransactionsMapper transactionMapper;
    private final IamportClient iamportClient;

    @Transactional
    public void verifyPayment(ReqVerifyPaymentDto dto, Integer memberNo) {
        try {
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient
                    .paymentByImpUid(dto.getImpUid());
            BigDecimal paidAmountFromIamport = iamportResponse.getResponse().getAmount();
            BigDecimal paidAmountFromDto = dto.getAmount();

            if (paidAmountFromIamport.compareTo(paidAmountFromDto) != 0) {
                throw new BusinessException(ResponseCode.INCONSISTENCY);
            }

            int goodsUpdateResult = goodsMapper.updateGoodsStatus("03", dto.getGoodsId());
            if (goodsUpdateResult == 0)
                throw new BusinessException(ResponseCode.NOT_FOUND);

            Integer sellerNo = goodsMapper.selectGoodsMemberNo(dto.getGoodsId());

            Transactions transactions = Transactions.builder()
                    .buyerNo(memberNo)
                    .sellerNo(sellerNo)
                    .price(paidAmountFromIamport.intValue())
                    .goodsId(dto.getGoodsId())
                    .build();

            transactionMapper.insertTranaction(transactions);

            Payment payment = Payment.builder()
                    .paymentId(dto.getImpUid())
                    .transactionId(transactions.getTransactionId())
                    .paymentMethod("inicis")
                    .paymentPrice(paidAmountFromIamport.intValue())
                    .build();

            paymentMapper.insertPayment(payment);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResPaymentAddressDto selectPaymentAddress(Integer memberNo, Integer bidId) {
        return paymentMapper.selectPaymentAddress(memberNo, bidId);
    }

    public ResPaymentAddressDto selectBuyoutPaymentAddress(Integer memberNo, Integer goodsId) {
        return paymentMapper.selectBuyoutPaymentAddress(memberNo, goodsId);
    }
}
