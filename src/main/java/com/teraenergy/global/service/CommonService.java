package com.teraenergy.global.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CommonService {
    
    /* 목록조회 */
    List<?> selectList(Object paramDTO, String queryId) throws Exception;

    /* 맵 조회 */
	Map<?, ?> selectMap(Object paramDTO, String queryId, String mapKey) throws Exception;

	/* 레코드 카운트 조회 */
	int selectCount(Object paramDTO, String queryId) throws Exception;

	/* 상세정보 조회 */
	Object selectContents(Object paramDTO, String queryId) throws Exception;

	/* 등록 */
	int insertContents(Object paramDTO, String queryId) throws Exception;

	/* 등록하고 키 반환 */
	Object insertSelectKey(Object paramDTO, String queryId) throws Exception;

	/* 수정 */
	int updateContents(Object paramDTO, String queryId) throws Exception;

	/* 삭제 */
	int deleteContents(Object paramDTO, String queryId) throws Exception;

}
