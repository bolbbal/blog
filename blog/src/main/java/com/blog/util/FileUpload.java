package com.blog.util;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.blog.domain.BoardAttachVo;

@Component
public class FileUpload {

	// String uploadFolder = "C:/upload";
	private final String uploadFolder = Paths.get("C:", "upload").toString();

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	public List<BoardAttachVo> uploadFiles(MultipartFile president,
			MultipartFile[] uploadFile) {

		List<BoardAttachVo> list = new ArrayList<>();

		String uploadFolderPath = getFolder();

		File uploadPath = new File(uploadFolder, uploadFolderPath);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		UUID uuid = UUID.randomUUID();

		String presidentOri = president.getOriginalFilename();

		presidentOri = uuid.toString() + "_" + presidentOri;

		File saveFile = new File(uploadPath, presidentOri);

		BoardAttachVo presi = new BoardAttachVo();

		try {
			president.transferTo(saveFile);

			presi.setUuid(uuid.toString());
			presi.setUploadpath(uploadFolderPath);
			presi.setCeoImg(president.getOriginalFilename());
			presi.setUploadfile(saveFile.toString().substring(10));
			presi.setFilename("null");

		} catch (Exception e) {
			e.printStackTrace();
		}

		list.add(presi);

		if (uploadFile[0].getSize() != 0) {
			for (MultipartFile multipartFile : uploadFile) {

				BoardAttachVo attach = new BoardAttachVo();

				String uploadFilesname = multipartFile.getOriginalFilename();

				uuid = UUID.randomUUID();

				uploadFilesname = uuid.toString() + "_" + uploadFilesname;

				File saveFilename = new File(uploadPath, uploadFilesname);
				System.out.println(saveFilename);
				try {

					multipartFile.transferTo(saveFilename);

					attach.setCeoImg("null");
					attach.setFilename(multipartFile.getOriginalFilename());
					attach.setUploadfile(saveFilename.toString().substring(10));
					attach.setUuid(uuid.toString());
					attach.setUploadpath(uploadFolderPath);

				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(attach);
			}
		}

		return list;
	}

}
