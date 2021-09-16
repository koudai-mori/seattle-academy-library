$(function() {
	var count = $('#rentCount').val();
	var stock = $('#stockCount').val();

	if (count === 0) {
		$('.btn_rentBook').prop('disabled', true);
		//$('.btn_deleteBook').prop('disabled', true);

	} 
	if (count === stock) {
		$('.btn_returnBook').prop('disabled', true);
	}

});