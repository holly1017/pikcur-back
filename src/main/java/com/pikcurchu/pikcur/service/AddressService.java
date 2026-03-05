package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqAddressDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.AddressMapper;
import com.pikcurchu.pikcur.vo.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressMapper addressMapper;

    public AddressService(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Transactional
    public Integer insertAddress(ReqAddressDto req, int memberNo) {
        int isDefaultCount = selectAddressIsDefault(memberNo);
        String isDefault = (isDefaultCount > 0) ? "N" : "Y";

        int result = addressMapper.insertAddress(req, memberNo, isDefault);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);

        return result;
    }

    public Integer selectAddressIsDefault(int memberNo) {
        return addressMapper.selectAddressIsDefault(memberNo);
    }

    public List<Address> selectAddressList(int memberNo) {
        return addressMapper.selectAddressList(memberNo);
    }

    @Transactional
    public Integer updateAddress(ReqAddressDto req, int memberNo) {
        int result = addressMapper.updateAddress(req, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }

    @Transactional
    public Integer deleteAddress(int addressId, int memberNo) {
        int result = addressMapper.deleteAddress(addressId, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);

        addressMapper.updateDefaultAddressAfterDelete(addressId, memberNo);
        return result;
    }

    @Transactional
    public Integer updateDefaultAddress(int addressId, int memberNo) {
        addressMapper.updateDefaultAddressN(memberNo);
        int result = addressMapper.updateDefaultAddressY(addressId, memberNo);

        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }
}
