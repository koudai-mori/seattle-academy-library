<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
 <link href="https://fonts.googleapis.com/css2?family=Dancing+Script&display=swap" rel="stylesheet">
 <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Mate+SC&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/hedder.js" /></script>
<script src="resources/js/disabled.js" /></script>
</head>
<body class="wrapper">
 <!--     <script type="text/javascript" src="hedder.js"></script>  -->
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logoByMori.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <div class="hamburger">
                <span></span> <span></span> <span></span> 
            </div>
            <nav class="globalMenuSp">
                <ul class=hambergermenu>
                    <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                    <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li> 
                    <li><a href="<%=request.getContextPath()%>/account" class="account">アカウント修正</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a> <a href="<%=request.getContextPath()%>/bulkRegist" class="btn_bulk_book">一括登録</a>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="post" class="book_thumnail" action="<%=request.getContextPath()%>/details">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                            </ul>
                            <ul>
                                <li class="book_title">${bookInfo.author}</li>
                            </ul>
                            <ul>
                                <li class="book_title">${bookInfo.publisher}</li>
                            </ul>
                            <ul>
                                <li class="book_title">${bookInfo.publishDate}</li>
                            </ul>
                           <%-- <ul>
                                <li class="book_title">${bookInfo.thumbnail}</li> 
                            </ul> --%>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>