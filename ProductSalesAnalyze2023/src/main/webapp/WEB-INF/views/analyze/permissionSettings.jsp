<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="/WEB-INF/views/analyze/headerIndex.jsp" %>

<style>
	body {
		background-color: #F0F0F0
	}
</style>

<div class="w-50 bg-white" style="margin: 0 auto; border: 1px">
	<ul class="nav nav-tabs mt-3 justify-content-center" id="myTab" role="tablist">
	  <li class="nav-item" role="presentation">
	    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button" role="tab" aria-controls="home" aria-selected="true">新增員工</button>
	  </li>
	  <li class="nav-item" role="presentation">
	    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="false">員工設定</button>
	  </li>
	  <li class="nav-item" role="presentation">
	    <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button" role="tab" aria-controls="contact" aria-selected="false">身分設定</button>
	  </li>
	</ul>
	<div class="tab-content bg-white" id="myTabContent">
	  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">...</div>
	  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>
	  <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>
	</div>
</div>



<%@include file="/WEB-INF/views/analyze/footer.jsp" %>