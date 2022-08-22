package com.teraenergy.global.service.impl;

import com.teraenergy.global.mapper.CommonMapper;
import com.teraenergy.global.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Resource(name="commonMapper")
	private CommonMapper commonMapper;
	@Override
    public List<?> selectList(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.selectList(paramDTO, queryId);
    }

    @Override
    public Map<?, ?> selectMap(final Object paramDTO, final String queryId, final String mapKey) throws Exception {
        return commonMapper.selectMap(paramDTO, queryId, mapKey);
    }

    @Override
    public int selectCount(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.selectCount(paramDTO, queryId);
    }

    @Override
    public Object selectContents(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.selectContents(paramDTO, queryId);
    }

    @Override
    public int insertContents(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.insertContents(paramDTO, queryId);
    }

    @Override
    public Object insertSelectKey(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.insertSelectKey(paramDTO, queryId);
    }

    @Override
    public int updateContents(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.updateContents(paramDTO, queryId);
    }

    @Override
    public int deleteContents(final Object paramDTO, final String queryId) throws Exception {
        return commonMapper.deleteContents(paramDTO, queryId);
    }
}
