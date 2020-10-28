异步渲染图片
当你在调用gallerify()方法之后，每次你添加新图片到图片画廊容器.photo中时，都需要进行异步加载图片。
$('.photos').gallerify(); 
$('.photos').append('<img src="randomimage.jpg">');
$('.photos').append('<img src="randomimage2.jpg">');
$('.photos').append('<img src="randomimage3.jpg">');
$('.photos').gallerify.renderAsyncImages();


官网文档地址：https://www.jquerycards.com/media/galleries/xgallerify/