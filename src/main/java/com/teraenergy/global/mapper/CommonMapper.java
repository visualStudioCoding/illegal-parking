package com.teraenergy.global.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository("commonMapper")
public class CommonMapper {

	@Autowired
    private SqlSessionTemplate template;

    private static final String PACKAGE_NAME = "pa.";

	/* 목록 조회 */
    public List<?> selectList(final Object paramDTO, final String queryId) throws Exception {
        String mQueryId = "";
        if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
            mQueryId = queryId + ".selectList";
        } else {
            mQueryId = queryId;
        }
        return template.selectList(PACKAGE_NAME + mQueryId, paramDTO);
    }
    
    /* 맵 조회 */
	public Map<?, ?> selectMap(final Object paramDTO, final String queryId, final String mapKey) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".selectMap";
		} else {
			mQueryId = queryId;
		}
		return template.selectMap(PACKAGE_NAME + mQueryId, paramDTO, mapKey);
	}

	/* 레코드 카운트 조회 */
	public int selectCount(final Object paramDTO, final String queryId) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".selectCount";
		} else {
			mQueryId = queryId;
		}
		return (Integer) template.selectOne(PACKAGE_NAME + mQueryId, paramDTO);
	}

	/* 상세 조회 */
	public Object selectContents(final Object paramDTO, final String queryId) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".selectContents";
		} else {
			mQueryId = queryId;
		}
		return template.selectOne(PACKAGE_NAME + mQueryId, paramDTO);
	}

	/* 등록 */
	public int insertContents(final Object paramDTO, final String queryId) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".insertContents";
		} else {
			mQueryId = queryId;
		}
		return template.insert(PACKAGE_NAME + mQueryId, paramDTO);
	}

	/* 수정 */
	public int updateContents(final Object paramDTO, final String queryId) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".updateContents";
		} else {
			mQueryId = queryId;
		}
		return template.update(PACKAGE_NAME + mQueryId, paramDTO);
	}

	/* 삭제 */
	public int deleteContents(final Object paramDTO, final String queryId) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".deleteContents";
		} else {
			mQueryId = queryId;
		}
		return template.delete(PACKAGE_NAME + mQueryId, paramDTO);
	}

	/* 정보를 입력하고 입력한 키 반환 */
	public Object insertSelectKey(final Object paramDTO, final String queryId) throws Exception {
		String mQueryId = "";
		if (queryId != null && (queryId.equals("") || !queryId.contains("."))) {
			mQueryId = queryId + ".insertSelectKey";
		} else {
			mQueryId = queryId;
		}
		template.insert(PACKAGE_NAME + mQueryId, paramDTO);
		return paramDTO;
	}
}
