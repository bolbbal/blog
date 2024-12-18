package com.blog.controller;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.domain.BoardAttachVo;
import com.blog.domain.BoardVo;
import com.blog.domain.CommentVo;
import com.blog.service.BoardService;
import com.blog.service.CommentService;
import com.blog.util.Criteria;
import com.blog.util.FileUpload;
import com.blog.util.PageVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/port")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	private final FileUpload fileUpload;
	private final CommentService commentService;

	@GetMapping("/write.do")
	// @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PreAuthorize("isAuthenticated()")
	public String writeBoardForm(Principal principal, Model model) {
		if (principal != null) {
			model.addAttribute("user", principal.getName());
			return "/portfolio/write";
		} else {
			return "redirect:/port/list.do";
		}

	}

	@PostMapping("/save.do")
	public String saveBoard(BoardVo board,
			@RequestParam("president") MultipartFile president,
			@RequestParam("uploadFile") MultipartFile[] uploadFile) {

		if (uploadFile != null || uploadFile[0].getSize() != 0) {
			List<BoardAttachVo> list = fileUpload.uploadFiles(president,
					uploadFile);

			board.setAttachList(list);
		}

		service.register(board);
		return "redirect:/port/list.do";
	}

	// @GetMapping("/list.do")
	// public String listBoard(Model model) {
	// List<BoardVo> list = service.getList();
	// Integer count = service.getBoardCount();
	//
	// model.addAttribute("list", list);
	// model.addAttribute("count", count);
	//
	// return "/portfolio/list";
	// }

	// 받�? 값을 ?��?��?���? ?���? ?��겨주?�� ?��?��로만 ?�� ?��?�� 매개�??���?
	// (@ModelAttribute("?��?���?") �??��???�� �??���?) ?�� ?��
	// ?��
	// ?��?��.

	@GetMapping("/list.do")
	public String listBoard(Criteria cri, Model model) {

		List<BoardVo> list = null;

		Integer count = 0;

		cri.setPageNum(1);
		cri.setAmount(5);

		list = service.getListPaging(cri);
		count = service.getBoardCountPaging(cri);

		model.addAttribute("pageMaker", new PageVo(cri, count));
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("cri", cri);

		return "/portfolio/portfolio";
	}

	@GetMapping("/view.do")
	public String viewBoard(@RequestParam("bno") Long bno, Model model) {

		BoardVo board = service.getDetail(bno);
		BoardVo next = service.getNext(bno);
		BoardVo prev = service.getPrev(bno);

		List<CommentVo> comments = commentService.getComments(bno);
		Long commentCount = commentService.getCommentCount(bno);

		model.addAttribute("view", board);
		model.addAttribute("next", next);
		model.addAttribute("prev", prev);

		model.addAttribute("comments", comments);
		model.addAttribute("commentCount", commentCount);

		return "/portfolio/view";
	}

	@GetMapping("/modify.do")
	public String modifyView(Long bno, Model model) {

		model.addAttribute("board", service.getDetail(bno));

		return "/portfolio/modify";
	}

	@PostMapping("/modify.do")
	public String modifyBoard(BoardVo board,
			@RequestParam("president") MultipartFile president,
			@RequestParam("uploadFile") MultipartFile[] uploadFile) {

		if (!president.isEmpty() && !uploadFile[0].isEmpty()) {

			List<BoardAttachVo> dlist = service.findByBno(board.getBno());

			removeFile(dlist);

			List<BoardAttachVo> list = fileUpload.uploadFiles(president,
					uploadFile);

			board.setAttachList(list);
		}

		service.updateBoard(board);

		return "redirect:/port/list.do";
	}

	public void removeFile(List<BoardAttachVo> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		list.forEach(attach -> {
			try {
				System.out.println("?��?��");
				Path filename = Paths
						.get("C:\\upload\\" + attach.getUploadfile());
				System.out.println(filename);
				Files.deleteIfExists(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@GetMapping("/delete.do")
	public String deleteBoard(Long bno, RedirectAttributes rttr) {

		List<BoardAttachVo> list = service.findByBno(bno);
		removeFile(list);

		if (service.deleteBoard(bno) == true) {

			rttr.addFlashAttribute("msg", "?��?��?���?");
			return "redirect:/port/list.do";

		} else {
			rttr.addFlashAttribute("msg", "?��?��?��?��");
			return "redirect:/port/list.do";
		}
	}

	// @PostMapping("/commentSave.do")
	// public String commentSave(CommentVo comment) {
	//
	// commentService.addComment(comment);
	//
	// return "redirect:/port/view.do?bno=" + comment.getCom_bno();
	//
	// }
	//
	// @PostMapping("/commentModify.do")
	// public String commentModify(@ModelAttribute ReplyVo comment) {
	// service.updateComment(comment);
	//
	// return "redirect:/port/view.do?bno=" + comment.getBoard_bno();
	// }
	//
	// @PostMapping("/commentDelete.do")
	// @ResponseBody
	// public Map<String, Object> commentDelete(@RequestParam Long reply_bno,
	// @RequestParam Long board_bno) {
	// service.deleteComment(reply_bno); // ?���? ?��?�� 처리
	// System.out.println(1);
	// Map<String, Object> response = new HashMap<>();
	// response.put("status", "success");
	// response.put("redirectUrl", "/port/view.do?bno=" + board_bno);
	// return response; // JSON ?��?���? ?��?��
	// }

	private final String uploadDir = "C:/upload";

	@GetMapping("/download.do")
	public ResponseEntity<Resource> downloadFile(
			@RequestParam("filename") String filename) {
		// ResponseEntity: Spring에서 HTTP 응답을 만들 때 사용하는 클래스입니다. HTTP 상태 코드, 헤더,
		// 바디를 모두 설정할 수 있습니다.
		// Resource: Spring의 Resource는 파일과 같은 리소스를 다루는 인터페이스입니다. 이를 통해 파일을 읽고
		// 응답으로 전달할 수 있습니다
		// @RequestParam: HTTP 요청에서 URL 파라미터를 메서드의 파라미터에 바인딩하는 어노테이션입니다
		System.out.println(filename);
		try {
			filename = filename.replace("\\", "/");
			System.out.println(filename);
			// 파일 경로에 포함된 백슬래시(\)를 슬래시(/)로 변경하는 코드입니다
			Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
			// Paths.get(uploadDir): uploadDir 변수에 저장된 디렉토리 경로를 기반으로 Path 객체를
			// 생성합니다.
			// resolve(filename): filename을 기존 경로에 결합합니다. 예를 들어, C:/uploads와
			// image.jpg를 결합하면 C:/uploads/image.jpg가 됩니다.
			// normalize(): 경로에 포함된 ..(상위 디렉터리로 이동)나 .(현재 디렉터리) 같은 특수 문자를 처리해
			// 정규화된 경로를 반환합니다. 예를 들어, C:/uploads/../image.jpg는 C:/image.jpg로
			// 변환됩니다.
			Resource resource = new UrlResource(filePath.toUri());
			// Resource는 파일과 같은 리소스를 다루는 인터페이스입니다. 이를 통해 파일을 읽고 응답으로 전달할 수 있습니다.
			// filePath.toUri(): Path 객체를 URI로 변환하여 UrlResource에 전달합니다. 이는 파일
			// 경로를 URL로 변환하는 과정입니다.
			if (!resource.exists() || !resource.isReadable()) {
				// resource.isReadable(): 리소스가 읽을 수 있는지 확인하는 메서드입니다. 파일이 존재하고 읽을
				// 수 있으면 true, 아니면 false를 반환합니다.
				throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다.");
			}
			String contentDisposition = "attachment; filename=\""
					+ resource.getFilename() + "\"";
			// Content-Disposition 헤더: 이 HTTP 헤더는 파일을 다운로드할 때 브라우저에게 파일을 다운로드로
			// 처리하도록 지시합니다.
			// attachment; filename=\"" + resource.getFilename() + "\"": 파일을
			// 다운로드할 때 브라우저가 보여줄 파일 이름을 설정하는 부분입니다.
			// 예를 들어, 파일 이름이 image.jpg라면, attachment; filename="image.jpg"가 됩니다.
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
					.body(resource);
			// ResponseEntity.ok(): HTTP 200 OK 상태 코드로 응답을 반환하겠다는 의미입니다
			// HttpHeaders.CONTENT_DISPOSITION: HTTP 헤더 중 Content-Disposition
			// 헤더를 설정합니다. 이 헤더는 브라우저에게 응답이 첨부파일임을 알려주고, 파일 이름을 설정합니다.
			// contentDisposition에 설정된 값이 Content-Disposition 헤더로 전달되어 다운로드할 파일
			// 이름이 지정됩니다.
			// body(resource): 응답 본문에 실제 파일 리소스를 담아서 클라이언트에게 전달합니다.
		} catch (MalformedURLException e) {
			// MalformedURLException: URL이 잘못된 형식일 때 발생하는 예외입니다. 이 예외가 발생하면, 파일
			// 경로가 잘못되었음을 알리고 처리합니다.
			throw new RuntimeException("파일 경로를 읽을 수 없습니다.", e);
		}
	}
}
