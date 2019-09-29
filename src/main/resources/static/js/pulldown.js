var defaultGrandChildText = "- Category -";
var defaultGrandChildValue = 0;
var defaultGrandChildOption = {value : defaultGrandChildValue , text: defaultGrandChildText,selected:true};
var defaultGrandChild = $('<option>',defaultGrandChildOption);
$('#parent').append(defaultGrandChild);

var defaultGrandChildText = "- grandChild -";
var defaultGrandChildValue = 0;
var defaultGrandChildOption = {value : defaultGrandChildValue , text: defaultGrandChildText,selected:true};
var defaultGrandChild = $('<option>',defaultGrandChildOption);
$('#grand-child').append(defaultGrandChild);

var defaultChildText = "- childCategory -";
var defaultChildValue = 0;
var defaultChildOption = {value : defaultChildValue , text: defaultChildText,selected:true};
var defaultChild = $('<option>',defaultChildOption);

$('#child').append(defaultChild);
//大カテゴリ
$(function(){
	// JSONデータへのパス
	var parentPath = "http://localhost:8080/merukari/categoryList/getParentCategory";  // httpから始まるURL型式でもOKです。
    // JSONデータを取得し、配列に格納する
    $.getJSON(parentPath, function(data){
		var parentList = new Map();
		//JSONデータをマップに格納
		$.each(data, function(key,val){
			parentList.set(key,val);
		});
		
		//マップに格納したデータをリストで回す
		parentList.forEach(function(value, key){
			for(var i = 0 ; i <= value.length-1 ; i++){
				//selectにセット
				var parentType = {value: key, text: value[i], selected:false}
				var parentCategory = $('<option>', parentType);
				$('#parent').append(parentCategory);
			}
		});
    	
		var noCategoryText = "no-category";
		var noCategoryValue = '';
		var noCategoryType = {value : noCategoryValue, text : noCategoryText, selected:false}
		var noCategory = $('<option>',noCategoryType);
		$('#parent').append(noCategory);
	});
});
//中カテゴリ
$('#parent').change(function(){
	//親要素のvalueを取得
	var parentVal = $('#parent').val();
	console.log(parentVal);
	//子要素のselectを削除
	$('#child').children().remove();
	
	var defaultChildText = "- childCategory -";
	var defaultChildValue = 0;
	var defaultChildOption = {value : defaultChildValue , text: defaultChildText,selected:true};
	var defaultChild = $('<option>',defaultChildOption);
	$('#child').append(defaultChild);
	
	$('#grand-child').children().remove();
	
	var defaultGrandChildText = "- grandChild -";
	var defaultGrandChildValue = 0;
	var defaultGrandChildOption = {value : defaultGrandChildValue , text: defaultGrandChildText,selected:true};
	var defaultGrandChild = $('<option>',defaultGrandChildOption);
	$('#grand-child').append(defaultGrandChild);
	
	$("#child").prop('disabled', false);
	
	var childPath = "http://localhost:8080/merukari/categoryList/getChildCategory";
	$.getJSON(childPath, function(data){
		var childList = new Map();
		$.each(data, function(key,val){
			childList.set(key,val);
		});
		
		childList.forEach(function(value, key){
			//親要素のvalueと子要素のparentを比較
			if(parentVal == key){
				for(var i = 0; i <= value.length-1; i++){
					Object.keys(value[i]).forEach(function(value){
						var childType = {value : value, text: this[value],selected:false}
						var childCategory = $('<option>',childType);
						$('#child').append(childCategory);
					},value[i])
				}
			}
		})
	})
});

//小カテゴリ
$('#child').change(function(){
	var childVal = $('#child').val();
	console.log(childVal);
	//子要素のselectを削除
	$('#grand-child').children().remove();
	
	var defaultGrandChildText = "- grandChild -";
	var defaultGrandChildValue = 0;
	var defaultGrandChildOption = {value : defaultGrandChildValue , text: defaultGrandChildText,selected:true};
	var defaultGrandChild = $('<option>',defaultGrandChildOption);
	$('#grand-child').append(defaultGrandChild);
	
	$("#grand-child").prop('disabled', false);
	
	var grandChildPath = "http://localhost:8080/merukari/categoryList/getGrandChildCategory"
		$.getJSON(grandChildPath, function(data){
			var grandChildList = new Map();
			$.each(data, function(key,val){
				grandChildList.set(key,val);
			})
			
		grandChildList.forEach(function(value,key){
			if(childVal == key){
				for(var i = 0; i <= value.length-1; i++){
					Object.keys(value[i]).forEach(function(value){
						var grandChildType = {value : value, text: this[value],selected:false}
						var grandChildCategory = $('<option>',grandChildType);
						$('#grand-child').append(grandChildCategory);
					},value[i])
				}
			}
		})
	})
})

$('#grand-child').change(function(){
	var grandChildVal = $('#grand-child').val();
	console.log(grandChildVal);
})

