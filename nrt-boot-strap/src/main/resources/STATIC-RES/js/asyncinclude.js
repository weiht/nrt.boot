;
(function($) {
$(function() {
	$('.async-include').each(function() {
		var me = $(this);
		var path = me.data('href') || me.attr('data-href');
		if (!path) return;
		
		$.get(path, {}, function(result) {
			me.html(result);
		}, 'html');
	});
});
})(jQuery);