<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>복슬 메모장</title>
<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/util.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/jquery-ui.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css"/>" />
<script type="text/javascript">
	function Ctmemo(rootPath){
		// === 전역 변수
		// 모든 링크 시작값
		this.contextRoot = rootPath;
		// 메모장에 적용될 스타일
		this.styleListAll;
		// 삭제된 메모 아이디 저장. 큐 형태로 활용
		this.deleteQueue = [];
		
		var instance = this;
		
		this.init = function(){
			this.loadAllMemo();
			$("._new").on("click", function(){
				instance.newMemo();			
			});
			
			$("._undelete").on("click", function(){
				instance.undeleteMemo();			
			});
			
			$("._search").keyup(function(){
				instance.search($(this).val());
			});

			// 각 메모장 상단 버튼 이벤트
			$("#space").on("click", "._delete", function(){
				var eventObj = $(this).parents("._item");
				instance.deleteMemo(eventObj);
			});
			
			$("#space").on("click", "._edit", function(){
				var eventObj = $(this).parents("._item");
				instance.editMemo(eventObj);
			});
			
			$("#space").on("click", "._done", function(){
				var eventObj = $(this).parents("._item");
				instance.editMemoDone(eventObj);
			});		
			
			$("#space").on("click", "._style", function(event){
				var eventObj = $(this).parents("._item");
				instance.choiceStyle(eventObj, event);
			});		
			
			// 팔레트 항목 클릭
			$("._style_palette").on("click", "div", function(){
				instance.applyPalette(this);
			});		
			
			$.get(instance.contextRoot + "/ctmemo/listUsagestyle.json.do", function(styleList) {
				instance.styleListAll = styleList;
				instance.loadPalette(styleList);
			});
			
			// 검색 텍스트 검색 취소 버튼.
			$(document).on('input', '.clearable', function() {
		    $(this)[instance.tog(this.value)]('x');
			}).on('mousemove', '.x', function(e) {
			    $(this)[instance.tog(this.offsetWidth-18 < e.clientX-this.getBoundingClientRect().left)]('onX');   
			}).on('click', '.onX', function(){
			    $(this).removeClass('x onX').val('').change();
			    instance.search("");
			});			
		}
		
		// 팔레트 적용
		this.loadPalette = function(styleList){
			var bg = $("._style_palette ._bg");
			styleList["bg"].forEach(function(entry){
				var area = $("<li/>").append("<div/>")
				area.find("div").addClass(entry);
				bg.append(area);
			});
			
			var font = $("._style_palette ._font");
			styleList["font"].forEach(function(entry){
				var area = $("<li/>").append("<div>T</div>")
				area.find("div").addClass(entry);
				font.append(area);
			});
		}
		
		// 새로운 메모를 생성한다.
		this.newMemo = function(){
			$.get(instance.contextRoot + "/ctmemo/newMemo.json.do", function(memo) {
				instance.displayMemo(memo);
				var newElement = $("._item[data-ctmemo_seq='"+memo.ctmemoSeq+"']")
				instance.editMemo(newElement);
			});
		}

		// 전체 메모장을 불러온다.
		this.loadAllMemo = function(){
			$.get(instance.contextRoot + "/ctmemo/listAllCtmemo.json.do", function(memoList) {
				$.each(memoList, function() {
					instance.displayMemo(this);
				});
			});
		}
		
		// 한 개 메모를 화면에 표시
		this.displayMemo = function(memo){
			var item = $("<div id='draggable' class='memo _item'><span class='toolbar _header'></span><div class='itemContent _content'></div></div>");
			item.attr("data-ctmemo_seq",memo.ctmemoSeq);
			item.attr("data-bg_css",memo.bgCss);
			item.attr("data-font_css",memo.fontCss);
			item.attr("data-reg_date",memo.regDate);
			item.find("._content").append(newline2br(memo.content));
			item.css("left", memo.positionX)
				.css("top", memo.positionY)
				.css("z-index", memo.zIndex)
				.css("width", memo.width)
				.css("height", memo.height)
				.addClass(memo.bgCss)
				.addClass(memo.fontCss);
			
			var regDate = new Date(memo.regDate);
			item.find("._header").append(regDate.format("yy.MM.dd"));
			item.find("._header").append("<input type='button' value='D' class='_delete'/>");
			item.find("._header").append("<input type='button' value='E' class='_edit'/>");
			item.find("._header").append("<input type='button' value='Done' class='_done' />");
			item.find("._header").append("<input type='button' value='S' class='_style styleBtn'/>");
			item.find("._header ._done").hide();
			
			$("#space").append(item);		
			item.draggable({stop: function(eventObj){
				var element = $(eventObj.target);
				instance.saveMemo(element);
			}});
			item.resizable({   
				maxHeight: 300,
			  maxWidth: 300,
			  minHeight: 80,
			  minWidth: 130,
			  stop: function(eventObj){
					var element = $(eventObj.target);
					instance.saveMemo(element);
			  }
			});
		}
		
		// 추가 또는 변경된 메모장을 저장
		this.saveMemo = function(element){
			var data = {};
			data["ctmemoSeq"] = parseInt(element.attr("data-ctmemo_seq"));
			data["content"] = removeTags(br2newline(element.find("._content").html()));
			data["zIndex"] = parseInt(element.css("z-index"));
			data["width"] = parseInt(element.css("width").replace('px', ''));
			data["height"] = parseInt(element.css("height").replace('px', ''));
			data["positionX"] = parseInt(element.css("left").replace('px', ''));
			data["positionY"] = parseInt(element.css("top").replace('px', ''));
			data["bgCss"] = element.attr("data-bg_css");
			data["fontCss"] = element.attr("data-font_css");
			data["regDate"] = element.attr("data-reg_date");
			
			$.post(instance.contextRoot + "/ctmemo/saveMemo.do", data, function( zIndex ) {
				element.css("z-index", zIndex)
			});
		}
		
		// 메모 수정
		this.editMemo = function(editElement){
			// 편집 상태에선 드레그, 사이즈 조정 금지
			editElement.draggable("disable")
			editElement.resizable("disable")
			
			editElement.find("._header ._edit").hide();
			editElement.find("._header ._done").show();
			
			var content = br2newline(editElement.find("._content").html());
			content = removeTags(content);
			var width = editElement.find("._content").width() - 6;
			// .content 높이를 가져오지 못함
			var height = editElement.height() - 30;
			
			editElement.find("._content").html("");
			editElement.find("._content").append("<textarea>"+content+"</textarea>")
			editElement.find("textarea").css("width", width).css("height", height);
		}
		
		// 메모 수정 사항 서버 반영
		this.editMemoDone = function(editElement){
			editElement.draggable("enable")
			editElement.resizable("enable")

			editElement.find("._header ._edit").show();
			editElement.find("._header ._done").hide();
			var content = newline2br(editElement.find("._content textarea").val());
			editElement.find("._content").html("");
			editElement.find("._content").append(content)
			instance.saveMemo(editElement);
		}
		
		// 메모 삭제
		this.deleteMemo = function(deleteElement){
			var seq = deleteElement.attr("data-ctmemo_seq");
			$.post(instance.contextRoot + "/ctmemo/deleteMemo.do", {ctmemoSeq: seq}, function( data ) {
				instance.deleteQueue.push(seq);
				deleteElement.remove();
				instance.undeleteDisplay();
			});		
		}
		
		// 스타일 선택
		this.choiceStyle = function(choiceElement, event){
			var ctmemoSeq = choiceElement.attr("data-ctmemo_seq");
			$("._style_palette").attr("data-target_seq", ctmemoSeq);
			$("._style_palette").css("left", event.pageX);
			$("._style_palette").css("top", event.pageY);
			$("._style_palette").show();
		}
		
		// 스타일 적용
		this.applyPalette = function(choiceElement){
			var ctmemoSeq = $("._style_palette").attr("data-target_seq");
			var targetMemo = $("._item[data-ctmemo_seq='"+ctmemoSeq+"']");
			
			var choiceStyle = $(choiceElement).attr("class");
			var choiceType = $(choiceElement).parents("ul").attr("data-type");
			
			instance.styleListAll[choiceType].forEach(function(entry){
				targetMemo.removeClass(entry);
			});
			targetMemo.addClass(choiceStyle);
			if(choiceType == "bg"){
				targetMemo.attr("data-bg_css", choiceStyle);
			}
			else{
				targetMemo.attr("data-font_css", choiceStyle);
			}
			instance.saveMemo(targetMemo);
			$("._style_palette").hide();
		}
		
		// 삭제 취소 버튼 활성화 여부 
		this.undeleteDisplay = function(){
			$("._undelete").css("display", instance.deleteQueue.length == 0 ? "none" : "inline-block");
		}
		
		// 마지막 삭제 취소
		this.undeleteMemo = function(){
			var seq = instance.deleteQueue.pop();
			$.post(instance.contextRoot + "/ctmemo/undelete.json.do", {ctmemoSeq: seq}, function( memo ) {
				instance.displayMemo(memo);
				instance.undeleteDisplay();
			});	
		}
		
		// 메모 검색
		this.search = function(word){
			$("#space ._item").each(function(){
				var content = br2newline($(this).find("._content").html());
				content = removeTags(content);
				if(word.length == 0){
					$(this).removeClass("not_search");
				}
				else if(content.indexOf(word) == -1){
					$(this).addClass("not_search");
				}
				else{
					$(this).removeClass("not_search");
					content = replaceAll(content, word, "<span>" + word + "</span>");
				}
				$(this).find("._content").html(newline2br(content));
			});
		}
		
		// 검색 취소 버튼 스타일 
		this.tog = function(v){
			return v ? 'addClass':'removeClass';
		} 	
	};
	
	$(function() {
		var ctmemo = new Ctmemo("<%=request.getContextPath()%>");
		ctmemo.init();
	});
</script>
</head>
<body>
	<div id="space">
		<div class="tool">
			<input type="text" value="" class="clearable _search"/>
			<input type="button" value="New" class="_new" /> 
			<input type="button" value="Undel" class="_undelete" style="display: none;"/>
		</div>
	</div>
	
	<div class="palette _style_palette">
		<ul class="_bg" data-type="bg">
			<!-- bgstyle -->
		</ul>
		<ul class="text_area _font" data-type="font">
			<!-- fontstyle -->
		</ul>
	</div>
	
</body>
</html>