<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  
<meta charset="UTF-8">
  
<title>書籍の編集｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>   
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
  
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
  
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
  
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
  
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
  
<script src="resources/js/thumbnail.js"></script>
    
<script src="resources/js/addBtn.js"></script>

<script src="resources/js/hedder.js" /></script>
</head>
<body class="wrapper">
      <script type="text/javascript" src="hedder.js"></script> 
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
                    <li><a href="<%=request.getContextPath()%>/mypage" class="mypage">マイページ</a></li>
                </ul>
            </nav>
        </div>
          
    </header>
      
    <main>
        <form action="<%=request.getContextPath()%>/updateBook" method="post" enctype="multipart/form-data" id="data_upload_form">
            <h1>書籍の編集</h1>
            <div class="content_body add_book_content">
                <div>
                    <span>書籍の画像</span> <span class="care care1">任意</span>
                    <div class="book_thumnail">
                        <img class="book_noimg" src="resources/img/noImg.png">
                    </div>
                    <input type="file" accept="image/*" name="thumbnail" id="thumbnail">
                </div>
                <div class="content_right">
                    <div>
                        <c:if test="${!empty errorMsgDate}">
                            <div class="error">${errorMsgDate}</div>
                        </c:if>
                        <c:if test="${!empty errorMsgIsbn}">
                            <div class="error">${errorMsgIsbn}</div>
                        </c:if>
                        <span>書籍名</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="title" value="${bookDetailsInfo.title}">
                        </c:if>
                        <c:if test="${empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="title" autocomplete="off">
                        </c:if>
                    </div>
                    <div>
                        <span>著者名</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="author" value="${bookDetailsInfo.author}">
                        </c:if>
                        <c:if test="${empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="author" autocomplete="off">
                        </c:if>
                    </div>
                    <div>
                        <span>出版社</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="publisher" value="${bookDetailsInfo.publisher}">
                        </c:if>
                        <c:if test="${empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="publisher">
                        </c:if>
                    </div>
                    <div>
                        <span>出版日</span><span class="care care2">必須</span>
                        <c:if test="${!empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="publish_date" value="${bookDetailsInfo.publishDate}">
                        </c:if>
                        <c:if test="${empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="publish_date">
                        </c:if>
                    </div>
                    <div>
                        <span>Isbn</span>
                        <c:if test="${!empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="isbn" value="${bookDetailsInfo.isbn}">
                        </c:if>
                        <c:if test="${empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="isbn">
                        </c:if>
                    </div>
                    <div>
                        <span>説明</span>
                        <c:if test="${!empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="description" value="${bookDetailsInfo.description}">
                        </c:if>
                        <c:if test="${empty bookDetailsInfo}">
                            <input type="text" id=addBookInput  name="description">
                        </c:if>
                    </div>
                    <input type="hidden" id="bookId" name="bookId" value="${bookDetailsInfo.bookId}">
                </div>
            </div>
            <div class="addBookBtn_box">
                <button type="submit" id="add-btn" class="btn_addBook">更新</button>
            </div>
        </form>
    </main>
</body>
</html>