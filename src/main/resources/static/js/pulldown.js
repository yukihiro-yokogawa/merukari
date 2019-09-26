//大カテゴリ
$(function(){
	// JSONデータへのパス
	var parentPath = "http://localhost:8080/merukari/categoryList/getParentCategory";  // httpから始まるURL型式でもOKです。
 
    // JSONデータを取得し、配列に格納する
    $.getJSON(parentPath, function(data){
		var parentList = new Map();
		$.each(data, function(key,val){
			parentList.set(key,val);
		});

	// 取得したJSONデータをコンソールに表示する
		parentList.forEach(function(value, key){
			for(var i = 0 ; i <= value.length-1 ; i++){
				var parentType = {value: key, text: value[i], selected:false}
				var parentCategory = $('<option>', parentType);
				$('#parent').append(parentCategory);
			}
		});
    	
		//中カテゴリ
		$('#parent').change(function(){
			var parentVal = $('#parent').val();
			console.log(parentVal);
			$('#child').children().remove();
			var childPath = "http://localhost:8080/merukari/categoryList/getChildCategory";
			$.getJSON(childPath, function(data){
				var childList = new Map();
				$.each(data, function(key,val){
					childList.set(key,val);
				});
				
				childList.forEach(function(value, key){
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
				
				//小カテゴリ
				$('#child').change(function(){
					var childVal = $('#child').val();
					console.log(childVal);
					$('#grand-child').children().remove();
					var grandChildPath = "http://localhost:8080/merukari/categoryList/getGrandChildCategory"
					$.getJSON(grandChildPath, function(data){
						var grandChildList = new Map();
						$.each(data, function(key,val){
							grandChildList.set(key,val);
						})
						
						console.log(grandChildList);
						
						grandChildList.forEach(function(value,key){
							if(childVal == key){
								for(var i = 0; i <= value.length-1; i++){
									var grandChildType = {value : key, text: value[i],selected:false}
									var grandChildCategory = $('<option>',grandChildType);
									$('#grand-child').append(grandChildCategory);
								}
							}
						})
					})
				})
			})
		});
	});
});

//中カテゴリ
$(function(){
	// JSONデータへのパス
	var childPath = "http://localhost:8080/merukari/categoryList/getChildCategory";  // httpから始まるURL型式でもOKです。
 
    // JSONデータを取得し、配列に格納する
	$.getJSON(childPath, function(data){
		var childList = new Map();
		$.each(data, function(key,val){
			childList.set(key,val);
		});
	// 取得したJSONデータをコンソールに表示する
		childList.forEach(function(value, key){
			for(var i = 0; i <= value.length-1; i++){
				Object.keys(value[i]).forEach(function(value){
					var childType = {value : value, text: this[value],selected:false}
					var childCategory = $('<option>',childType);
					$('#child').append(childCategory);
				},value[i])
			}
		})
		
		//小カテゴリ
		$('#child').change(function(){
			var childVal = $('#child').val();
			var childText = $('#child').children().text();
			
			console.log(childVal);
			$('#grand-child').children().remove();
			var grandChildPath = "http://localhost:8080/merukari/categoryList/getGrandChildCategory"
			$.getJSON(grandChildPath, function(data){
				var grandChildList = new Map();
				$.each(data, function(key,val){
					grandChildList.set(key,val);
				})
												
				grandChildList.forEach(function(value,key){
					if(childVal == key){
						for(var i = 0; i <= value.length-1; i++){
							var grandChildType = {value : key, text: value[i],selected:false}
							var grandChildCategory = $('<option>',grandChildType);
							$('#grand-child').append(grandChildCategory);
						}
					}
				})
			})
		})
	});
});

$(function(){
	// JSONデータへのパス
	var grandChildPath = "http://localhost:8080/merukari/categoryList/getGrandChildCategory"
	$.getJSON(grandChildPath, function(data){
		var grandChildList = new Map();
		$.each(data, function(key,val){
			grandChildList.set(key,val);
		})
										
		grandChildList.forEach(function(value,key){
			for(var i = 0; i <= value.length-1; i++){
				var grandChildType = {value : key, text: value[i],selected:false}
				var grandChildCategory = $('<option>',grandChildType);
				$('#grand-child').append(grandChildCategory);
			}
		})
	})
})

