$(function() {
	var status=$('#RentingStatus').val();
	
	if (status==='貸し出し可') {
           $('.btn_returnBook').prop('disabled', true);
        } else if(status==='貸し出し中') {
			$('.btn_rentBook').prop('disabled',true);
			$('.btn_deleteBook').prop('disabled',true);
        }
});