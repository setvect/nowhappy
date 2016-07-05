package com.setvect.nowhappy.code.service;

import java.util.List;

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
}
