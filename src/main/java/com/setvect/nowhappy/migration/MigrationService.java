package com.setvect.nowhappy.migration;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import com.setvect.nowhappy.ApplicationConstant.FileUpload;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.service.AttachFileService;
import com.setvect.nowhappy.attach.vo.AttachFileVo;
import com.setvect.nowhappy.board.repository.BoardArticleRepository;
import com.setvect.nowhappy.board.repository.BoardRepository;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.util.FileUtil;

/**
 * 첨부파일 마이그레이션..
 */
@Service
public class MigrationService {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private BoardRepository boardRepository;
	@Inject
	private BoardArticleRepository boardArticleRepository;

	public String runMigration(File destDir) {

		Query s = em.createNativeQuery("delete from TBYA_ATTACH_FILE ");
		s.executeUpdate();

		String q = " select FILE_SEQ, ARTICLE_SEQ, ORIGINAL_NAME, SAVE_NAME, SIZE from TBBD_BOARD_FILE order by FILE_SEQ";
		Query query = em.createNativeQuery(q);
		List<Object[]> result = query.getResultList();

		File uploadPath = new File(destDir, FileUpload.UPLOAD_PATH);
		File attachPath = new File(destDir.getAbsoluteFile(), FileUpload.ATTACH_PATH);
		int count = 0;
		for (Object[] row : result) {
			count++;
			int fileSeq = (Integer) row[0];
			int articleSeq = (Integer) row[1];
			String orgName = (String) row[2];
			String saveName = (String) row[3];
			int size = (Integer) row[4];

			BoardArticleVo boardArticle = boardArticleRepository.findOne(articleSeq);

			File saveDayPath = FileUtil.makeDayDir(attachPath, boardArticle.getRegDate());
			String dayPath = AttachFileService.getDayPath(attachPath, saveDayPath);

			File sourcePath = new File(uploadPath, "board/" + boardArticle.getBoardCode());
			File sourceFile = new File(sourcePath, saveName);

			String fileName = AttachFileModule.BOARD + "_" + articleSeq + "." + FilenameUtils.getExtension(orgName);
			File moveFile = new File(saveDayPath, fileName);

			boolean r = sourceFile.renameTo(moveFile);
			System.out.printf("[%,d/%,d] %s,  %s => %s\n", count, result.size(), r, sourceFile, moveFile);

			AttachFileVo vf = new AttachFileVo();
			vf.setModuleName(AttachFileModule.BOARD);
			vf.setModuleId(String.valueOf(articleSeq));
			vf.setUserId("setvect");
			vf.setOriginalName(orgName);
			vf.setSaveName(dayPath + fileName);
			vf.setSize(size);
			vf.setRegDate(boardArticle.getRegDate());
			em.persist(vf);
		}

		return "done.";
	}

}
