<%@page import="mapper.NoticeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp" %>
   <!-- sub contents -->
   <div class="sub_title">
      <h2>공지사항</h2>
      <div class="container">
        <div class="location">
         <ul>
            <li class="btn_home">
               <a href="/np/"><i class="fa fa-home btn_plus"></i></a>
            </li>
            <li class="dropdown">
               <a href="">커뮤니티<i class="fa fa-plus btn_plus"></i></a>
               <div class="dropdown_menu">
                  <a href="/np/list.do">공지사항</a>
                  <a href="allclass.html">학과및모집안내</a>
                  <a href="portfolio.html">포트폴리오</a>
                  <a href="online.html">온라인접수</a>
                  <a href="notice.html">커뮤니티</a>
               </div>
            </li>
            <li class="dropdown">
               <a href="/np/list.do">공지사항<i class="fa fa-plus btn_plus"></i></a>
               <div class="dropdown_menu">
                  <a href="/np/list.do">공지사항</a>
                  <a href="qa.html">질문과답변</a>
                  <a href="faq.html">FAQ</a>
               </div>
            </li>
         </ul>
        </div>
      </div><!-- container end -->
   </div>

   <c:set var="num" value="${count-(page.cri.pageNum-1)*10}"/>
	<div class="container">
	  <div class="search_wrap">
		<div class="record_group">
			<p>총게시글<span>${count}</span>건</p>
		</div>
		<div class="search_group">
			<form name="myform" method="get" action="/np/list.do" onsubmit="return check();">
				<select name="type" class="select">
					<option value="">선택</option>
					<option value="title" ${page.cri.type.equals('title')?'selected':'' }>제목</option>
					<option value="content" ${page.cri.type.equals('content')?'selected':'' }>내용</option>
				</select>
				<input type="text" name="keyword" class="search_word" value="${page.cri.keyword}">
				<button class="btn_search" type="submit"><i class="fa fa-search"></i><span class="sr-only">검색버튼</span></button>
			</form>
		</div>
	  </div> <!-- search end -->
	  <div class="bord_list">
		<table class="bord_table" summary="이표는 번호,제목,글쓴이,날자,조회수로 구성되어 있습니다">
			<caption class="sr-only">공지사항 리스트</caption>
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="10%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>
			
			<tbody>
				<c:if test="${list.isEmpty() || list == null}">
					<tr>
						<td colspan="5">검색 결과가 존재하지 않습니다.</td>
					</tr>
				</c:if>
				<!--<c:if test="${count == 0}">
					<tr>
						<td colspan="5">검색 결과가 존재하지 않습니다.</td>
					</tr>
				</c:if>-->
				<c:forEach var="list" items="${list}">
					<tr>
						<td>${num}</td>
						<td class="title"><a href="/np/view.do?idx=${list.getIdx() }">${list.getTitle() }</a></td>
						<td>${list.getWriter() }</td>
						<td>${list.getRegdate() }</td>
						<td>${list.getViewcount() }</td>
					</tr>
					<c:set var="num" value="${num-1}"/>
				</c:forEach>
			</tbody>
		</table>
		<div class="paging">
			<c:if test="${page.prev }">
				<a href="?pageNum=1&type=${page.cri.getType() }&keyword=${page.cri.getKeyword()}"><i class="fa  fa-angle-double-left"></i></a>
			</c:if>
			<c:if test="${page.cri.pageNum!=1 }">
				<a href="?pageNum=${page.cri.pageNum-1}&type=${page.cri.getType() }&keyword=${page.cri.getKeyword()}"><i class="fa fa-angle-left"></i></a>
			</c:if>
			<c:forEach var="pageNum" begin="${page.startPage }" end="${page.endPage }">
				<a href="?pageNum=${pageNum }&type=${page.cri.getType() }&keyword=${page.cri.getKeyword()}" class="${page.cri.pageNum==pageNum?'active':''}">${pageNum}</a>
			</c:forEach>
			<c:if test="${page.cri.pageNum!=page.endPage}">
				<a href="?pageNum=${page.cri.pageNum+1 }&type=${page.cri.getType() }&keyword=${page.cri.getKeyword()}"><i class="fa fa-angle-right"></i></a>
			</c:if>
			<c:if test="${page.next }">
				<a href="?pageNum=${page.realEnd}&type=${page.cri.getType() }&keyword=${page.cri.getKeyword()}"><i class="fa  fa-angle-double-right"></i></a>
			</c:if>
			<a href="/np/write.do" class="btn_write">글쓰기</a>
		</div>
	  </div>
	</div>
   <!-- end contents -->
   
   <script>
   /*function check() {
	   if(myform.type.value=="") {
		   alert("검색 방법을 선택하세요.");
		   myform.type.focus();
		   return false;
	   }
	   if(myform.keyword.value=="") {
		   myform.keyword.focus();
		   alert("검색 키워드가 없습니다!");
		   return false;
		   }
	   return true;
	   }*/
	   
	   function check() {
		   if($(".select").val()=="") {
			   alert("검색 방법 선택");
			   $(".select").focus();
			   return false;
		   }
		   if($(".search_word").val()="") {
			   alert("검색 키워드 입력");
			   $(".search_word").focus();
			   return false;
		   }
		   return true;
	   }
   
   
      $(function() {
         $(".location  .dropdown > a").on("click",function(e) {
            e.preventDefault();
            if($(this).next().is(":visible")) { // is(":visible")
               $(".location  .dropdown > a").next().hide();
            } else {
               $(".location  .dropdown > a").next().hide();
               $(this).next().show();
            }
         });
      });
   </script>
   

<%@ include file="/footer.jsp" %>









