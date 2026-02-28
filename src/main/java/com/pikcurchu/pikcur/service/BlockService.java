package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.response.ResBlockDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.BlockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlockService {
    private final BlockMapper blockMapper;

    public BlockService(BlockMapper blockMapper) {
        this.blockMapper = blockMapper;
    }

    public List<ResBlockDto> selectBlockList(Integer memberNo) {
        return blockMapper.selectBlockList(memberNo);
    }

    @Transactional
    public Integer deleteBlockStore(Integer blockId, Integer memberNo) {
        int result = blockMapper.deleteBlockStore(blockId, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return result;
    }
}
