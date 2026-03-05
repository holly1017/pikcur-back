package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.request.ReqShippingDto;
import com.pikcurchu.pikcur.dto.response.ResTransactionDetailDto;
import com.pikcurchu.pikcur.vo.Transactions;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionsMapper {
    ResTransactionDetailDto findTransactionInfoById(Integer transactionId);

    int insertShippingInfo(ReqShippingDto reqShippingDto);

    int updateTransactionStatus(Integer transactionId);

    int createTransaction(Integer buyerNo, Integer sellerNo, Integer goodsId, Integer bidPrice);

    int insertTranaction(Transactions transactions);
}
