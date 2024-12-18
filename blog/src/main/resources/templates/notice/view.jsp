<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/header.jsp"%>
<!-- sub contents -->
	<div class="sub_title">
		<h2>공지사항</h2>
		<div class="container">
		  <div class="location">
			<ul>
				<li class="btn_home">
					<a href="index.html"><i class="fa fa-home btn_plus"></i></a>
				</li>
				<li class="dropdown">
					<a href="">커뮤니티<i class="fa fa-plus btn_plus"></i></a>
					<div class="dropdown_menu">
						<a href="gratings.html">공지사항</a>
						<a href="allclass.html">학과및모집안내</a>
						<a href="portfolio.html">포트폴리오</a>
						<a href="online.html">온라인접수</a>
						<a href="notice.html">커뮤니티</a>
					</div>
				</li>
				<li class="dropdown">
					<a href="">질문답변<i class="fa fa-plus btn_plus"></i></a>
					<div class="dropdown_menu">
						<a href="gratings.html">공지사항</a>
						<a href="gratings.html">질문답변</a>
						<a href="gratings.html">취업실적</a>
					</div>
				</li>
			</ul>
		  </div>
		</div><!-- container end -->
	</div>

	<div class="container">
		<div class="board_view">
			<h2>${view.getTitle()}</h2>
			<p class="info"><span class="user">${view.getWriter() }</span> | ${view.getRegdate()} | <i class="fa fa-eye"></i> ${view.getViewcount() }</p>
			<div class="board_body">
				${view.getContent()}
			</div>
			<div class="prev_next">
			<c:if test="${prev.getIdx() != null }">
				<a href="/np/view.do?idx=${prev.getIdx()}" class="btn_prev"><i class="fa fa-angle-left"></i>
					<span class="prev_wrap">
						<strong>이전글</strong>
						<span>${prev.getTitle()}</span>
					</span>
				</a>
			</c:if>
			<c:if test="${prev.getIdx() == null }">
				<a href="" class="btn_prev"><i class="fa fa-angle-left"></i>
					<span class="prev_wrap">
						<strong>이전글</strong>
						<span>이전 글이 없습니다.</span>
					</span>
				</a>
			</c:if>
				<div class="btn_3wrap">
					<a href="/np/list.do">목록</a> 
					<a href="/np/modify.do?idx=${view.getIdx() }">수정</a> 
					<a href="/np/delete.do?idx=${view.getIdx()}" onClick="return confirm('삭제하시겠어요?')">삭제</a>
				</div>
				<c:if test="${next.getIdx() != null }">
					<a href="/np/view.do?idx=${next.getIdx()}" class="btn_next">
						<span class="next_wrap">
							<strong>다음글</strong>
							<span>${next.getTitle()}</span>
						</span>
						<i class="fa fa-angle-right"></i>
					</a>
				</c:if>
				<c:if test="${next.getIdx() == null }">
					<a href="" class="btn_next">
						<span class="next_wrap">
							<strong>다음글</strong>
							<span>다음 글이 없습니다.</span>
						</span>
						<i class="fa fa-angle-right"></i>
					</a>
				</c:if>
			</div>
		</div>
	</div>

	<!-- end contents -->
	
	<script>
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
<%@ include file = "/footer.jsp"%>