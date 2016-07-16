package com.setvect.nowhappy.code.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.nowhappy.code.repository.CodeRepository;
import com.setvect.nowhappy.code.vo.CodeVo;

@Service("CodeService")
public class CodeService {

	@Autowired
	private CodeRepository codeRepository;

	public CodeVo getCode(int codeSeq) {
		return codeRepository.findOne(codeSeq);
	}

	public List<CodeVo> listCode(String majorCode) {
		return codeRepository.findByMajorCodeOrderByOrderNoAsc(majorCode);
	}

	public void createOrUpdateCode(CodeVo codeVo) {
		codeRepository.save(codeVo);
	}

	public void removeCode(int seq) {
		codeRepository.delete(seq);
	}

	public Map<String, CodeVo> mapCode(String majorCode) {
		List<CodeVo> list = listCode(majorCode);
		Map<String, CodeVo> map = new HashMap<>();

		for (CodeVo i : list) {
			map.put(i.getMinorCode(), i);
		}
		return map;
	}
}
