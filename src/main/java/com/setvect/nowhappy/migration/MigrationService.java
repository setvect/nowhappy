package com.setvect.nowhappy.migration;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.setvect.nowhappy.ApplicationConstant.FileUpload;
import com.setvect.nowhappy.attach.service.AttachFileModule;
import com.setvect.nowhappy.attach.service.AttachFileService;
import com.setvect.nowhappy.attach.vo.AttachFileVo;
import com.setvect.nowhappy.board.dao.BoardArticleDao;
import com.setvect.nowhappy.board.dao.BoardDao;
import com.setvect.nowhappy.board.vo.BoardArticleVo;
import com.setvect.nowhappy.util.FileUtil;

/**
 * 첨부파일 마이그레이션..
 */
@Service
public class MigrationService {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@Inject
	private BoardDao boardDao;
	@Inject
	private BoardArticleDao boardArticleDao;

	public String runMigration(File destDir) {

		Session session = sessionFactory.getCurrentSession();
		SQLQuery s = session.createSQLQuery("delete from TBYA_ATTACH_FILE ");
		s.executeUpdate();

		String q = " select FILE_SEQ, ARTICLE_SEQ, ORIGINAL_NAME, SAVE_NAME, SIZE from TBBD_BOARD_FILE order by FILE_SEQ";
		SQLQuery query = session.createSQLQuery(q);
		List<Object[]> result = query.list();

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

			BoardArticleVo boardArticle = boardArticleDao.getArticle(articleSeq);

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
			session.save(vf);
		}

		return "done.";
	}

}
