 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<!-- sub contents -->
	<div class="sub_title">
		<h2>포트폴리오</h2>
		<div class="container">
		  <div class="location">
			<ul>
				<li class="btn_home">
					<a href="/"><i class="fa fa-home btn_plus"></i></a>
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
						<a href="/port/list.jsp">포트폴리오</a>
					</div>
				</li>
			</ul>
		  </div>
		</div><!-- container end -->
	</div>

	
	<div class="container">
	  <div class="search_wrap">
		<div class="record_group">
			<p>총게시글<span>${count}</span>건</p>
		</div>
		<div class="search_group">
			<form name="myform" method="get" action="/port/list.do">
				<select name="type" class="select">
					<option value="title" ${page.cri.type.equals('title')?'selected':'' }>제목</option>
					<option value="content" ${page.cri.type.equals('content')?'selected':'' }>내용</option>
					<option value="writer" ${page.cri.type.equals('writer')?'selected':'' }>글쓴이</option>
				</select>
				<input type="text" name="keyword" class="search_word" value="${page.cri.keyword}">
				<button class="btn_search"><i class="fa fa-search"></i><span class="sr-only">검색버튼</span></button>
			</form>
		</div>
	  </div> <!-- search end -->
	  <c:set var="num" value="${count - (page.cri.pageNum - 1) * 5}"/>
	  <div class="bord_list">
		<ul class="basic_board">
			<c:forEach var="list" items="${list }">
				
				<fmt:parseDate var="regdate" value="${list.regdate }" pattern="yyyy-MM-dd"/>
				<li>
					<span class="date"><em><fmt:formatDate value="${regdate }" pattern="dd"/></em><fmt:formatDate value="${regdate }" pattern="yyyy.MM"/></span>
					<div class="text_wrap">
						<div class="img_wrap" style="position: relative;">
							<c:forEach var="attach" items="${list.attachList }">
								<c:if test="${attach.ceoImg != 'null' }">
									<img src="/photo/${attach.uploadfile}" alt="">
								</c:if>
							</c:forEach>
						</div>
						
						<span class="info">
							<span class="blue_text">No. ${num }</span>
							<i class="bar"></i>
							<i class="fa fa-eye"></i> 5
						</span>
						<p class="title">
							<a href="/port/view.do?bno=${list.bno }">${list.title}</a>
						</p>
						<a id="downloadButton" 
			                  th:if="${!#list.isEmpty(blog.attachList)}" 
			                  th:data-filename="${blog.attachList[0].uploadpath + '/' + blog.attachList[0].uploadfile}" 
			                  style="background: #00f; color: #fff; display: inline-block; padding: 6px 12px; cursor: pointer;">
			                   다운로드
			               </a>
						
						
					</div>
					
				</li>
				<c:set var="num" value="${num - 1 }"/>
			</c:forEach>
		</ul>
		<div class="paging">
			<c:if test="${page.prev }">
				<a href="?pageNum=1<c:if test="${page.cri.type != null}">?&type=${page.cri.type }&keyword=${page.cri.keyword}</c:if>"><i class="fa  fa-angle-double-left"></i></a>
			</c:if>
			<c:if test="${page.cri.pageNum!=1 }">
				<a href="?pageNum=${page.cri.pageNum-1}<c:if test="${page.cri.type != null}">?&type=${page.cri.type }&keyword=${page.cri.keyword}</c:if>"><i class="fa fa-angle-left"></i></a>
			</c:if>
			<c:forEach var="pageNum" begin="${page.startPage }" end="${page.endPage }">
				<a href="?pageNum=${pageNum }<c:if test="${page.cri.type != null}">?&type=${page.cri.type }&keyword=${page.cri.keyword}</c:if>" class="${page.cri.pageNum==pageNum?'active':''}">${pageNum}</a>
			</c:forEach>
			<c:if test="${page.cri.pageNum!=page.endPage}">
				<a href="?pageNum=${page.cri.pageNum+1 }<c:if test="${page.cri.type != null}">?&type=${page.cri.type }&keyword=${page.cri.keyword}</c:if>"><i class="fa fa-angle-right"></i></a>
			</c:if>
			<c:if test="${page.next }">
				<a href="?pageNum=${page.realEnd}<c:if test="${page.cri.type != null}">?&type=${page.cri.type }&keyword=${page.cri.keyword}</c:if>"><i class="fa  fa-angle-double-right"></i></a>
			</c:if>
			
			<%-- <sec:authorize access="hasRole('ADMIN')">
				<a href="/port/write.do" class="btn_write">글쓰기</a>
			</sec:authorize> --%>
			
			<sec:authorize access="isAuthenticated()">
				<a href="/port/write.do" class="btn_write">글쓰기</a>
			</sec:authorize>
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
<%@ include file="../footer.jsp"%>