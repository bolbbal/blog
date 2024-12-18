<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../header.jsp"%>
<!-- sub contents -->
	<div class="sub_title">
		<h2>포트폴리오</h2>
		<div class="container">
		  <div class="location">
			<ul>
				<li class="btn_home">
					<a href="index.html"><i class="fa fa-home btn_plus"></i></a>
				</li>
				<li class="dropdown">
					<a href="">포트폴리오<i class="fa fa-plus btn_plus"></i></a>
					<div class="dropdown_menu">
						<a href="../about/gratings.html">기업소개</a>
						<a href="../portfolio/portfolio.html">포트폴리오</a>
						<a href="../notice/notice.html">커뮤니티</a>
					</div>
				</li>
				<li class="dropdown">
					<a href="">포트폴리오<i class="fa fa-plus btn_plus"></i></a>
					<div class="dropdown_menu">
						<a href="portfolio.html">포트폴리오</a>
					</div>
				</li>
			</ul>
		  </div>
		</div><!-- container end -->
	</div>

	<div class="container">
		<div class="board_view">
			<h2>${view.title }</h2>
			<p class="info"><span class="user">${view.writer }</span> | ${view.regdate } | <i class="fa fa-eye"></i> <%-- ${view.viewcount } --%></p>
			<div class="board_body">
				<p>${view.content }</p>
				<%-- <img src="${pageContext.request.contextPath}/upload/${view.imgurl}" alt=""> --%>
			</div>
			<div class="prev_next">
				<c:if test="${prev != null }">
					<a href="/port/view.do?bno=${prev.bno }" class="btn_prev"><i class="fa fa-angle-left"></i>
					<span class="prev_wrap">
						<strong>이전글</strong><span>${prev.title }</span>
					</span>
					</a>
				</c:if>
				<c:if test="${prev eq null }">
					<a href="/port/view.do?bno=${view.bno }" class="btn_prev"><i class="fa fa-angle-left"></i>
					<span class="prev_wrap">
						<strong>이전글</strong><span>이전 글이 없습니다.</span>
					</span>
					</a>
				</c:if>
				<div class="btn_3wrap">
					<a href="/port/list.do">목록</a> 
					<a href="/port/modify.do?bno=${view.bno}">수정</a> 
					<a href="/port/delete.do?bno=${view.bno}" onClick="return confirm('삭제하시겠어요?')">삭제</a>
				</div>
				<c:if test="${next != null }">
					<a href="/port/view.do?bno=${next.bno }" class="btn_next">
					<span class="prev_wrap">
						<strong>다음글</strong><span>${next.title }</span>
					</span>
					<i class="fa fa-angle-right"></i>
					</a>
				</c:if>
				<c:if test="${next eq null }">
					<a href="/port/view.do?bno=${view.bno }" class="btn_next">
					<span class="prev_wrap">
						<strong>다음글</strong><span>다음 글이 없습니다.</span>
					</span>
					<i class="fa fa-angle-right"></i>
					</a>
				</c:if>
			</div>
			
		</div>
	</div>
	
	   <!-- comment -->
      <div class="container">
           <div style="border-top:1px solid #ccc;">
           <p style="font-size: 22px; font-weight: bold; padding: 20px 0;">
              Comments: ${commentCount}
           </p>
           </div>
           <form name="comment" method="post" action="/port/commentSave.do" onsubmit="return cmtWrite()">
	           <div style="display: flex; justify-content: space-between;">
	           <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	           	<input type="hidden" name="board_bno" value="${view.bno }">
	           	<input type="hidden" name="username" value="${principal.username}">
	              <textarea name="content" class = "cmt_area" style="width: 88%; height: 130px; vertical-align: top; resize: none;"></textarea>
	              <button type="submit"id="btn_comment" style="width: 10%; height: 130px;">댓글등록</button>            
	           </div>
           </form>
           <div>
              <ul>
               	 <c:forEach var="comment" items="${view.commentList }">
				    <li style="padding:12px 0;">
				        <span>ㄴ</span> ${comment.username} ${comment.regdate}
				    </li>
				    <li>${comment.content}</li>
				    <button type="button" class="modify" data-reply-bno="${comment.reply_bno}">수정</button>
				    <form name="comment_modify_${comment.reply_bno}" method="post" action="/port/commentModify.do" onsubmit="return cmtModify()">
				        <button type="submit" class="delete" data-reply-bno="${comment.reply_bno}">삭제</button>
				        <div style="display: flex; justify-content: space-between;">
				            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				            <input type="hidden" name="reply_bno" value="${comment.reply_bno}">
				            <input type="hidden" name="board_bno" value="${comment.board_bno}">
				            <textarea name="content" class="content_modify" style="display: none; width: 88%; height: 130px; vertical-align: top; resize: none;">${comment.content}</textarea>
				            <button type="submit" class="btn_comment_modify" style="display: none; width: 10%; height: 130px;">댓글수정</button>
				        </div>
				    </form>
				</c:forEach>

                  
               </ul>
            </div>
         </div>

	<!-- end contents -->
	
	<script>
	 function cmtWrite(){ //댓글등록 
         
         var writer ="${principal.username}";
         var cmtContent =$(".cmt_area").val();
         
         if(writer == "") {
             alert("로그인 하세요");
             return false;
          }
         
         if(cmtContent == "") {
            alert ("댓글내용 입력 하세요");
            return false;
         }
         
        return true;
      } 
	 
	 function cmtModify() {
		var cmtContent =$("content_modify").val();
         
         if(writer == "") {
             alert("로그인 하세요");
             return false;
          }
         
         if(cmtContent == "") {
            alert ("댓글내용 입력 하세요");
            return false;
         }
         
        return true;
	 }
	 
	 $(document).ready(function () {
		    // "수정" 버튼 클릭 이벤트
		    $(".modify").on("click", function () {
		        var $parentForm = $(this).next("form"); // 클릭된 버튼 다음의 form 요소 선택
		        $parentForm.find(".content_modify").css("display", "block"); // textarea 표시
		        $parentForm.find(".btn_comment_modify").css("display", "block"); // 댓글수정 버튼 표시
		    });

		    var csrfToken = $('meta[name="_csrf"]').attr('content');
			var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
			
		    // "삭제" 버튼 클릭 이벤트
		    $(".delete").on("click", function (e) {
		        e.preventDefault(); // 기본 동작 중단
		        var reply_bno = $(this).data("reply-bno"); // data-reply-bno 속성 값 가져오기

		        if (confirm("정말 댓글을 삭제하시겠습니까?")) {
		            $.ajax({
		                type: "post",
		                url: "/port/commentDelete.do",
		                beforeSend: function (xhr) {
		                    xhr.setRequestHeader(csrfHeader, csrfToken); // CSRF 토큰 설정
		                },
		                data: { reply_bno: reply_bno,
		                	board_bno : "${view.bno}"},
		                success: function (response) {
		                    console.log("삭제 성공: ", response);
		                    alert("댓글이 삭제되었습니다.");
		                    location.reload(); // 페이지 새로고침
		                },
		                error: function (xhr, status, error) {
		                    console.error("삭제 실패: ", xhr.responseText);
		                    alert("댓글 삭제 중 오류가 발생했습니다.");
		                },
		            });
		        }
		    });

		});

	 
		$(function() {
			$(".location  .dropdown > a").on("click",function(e) {
				e.preventDefault();
				if($(this).next().is(":visible")) {
					$(".location  .dropdown > a").next().hide();
				} else {
					$(".location  .dropdown > a").next().hide();
					$(this).next().show();
				}
			});
		});
	</script>
<%@ include file = "../footer.jsp"%>