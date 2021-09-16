$(function() {
	var userIdSet = $('#userId').val();

	if (userIdSet.length !== 0) {
		sessionStorage.setItem(['userId'], [userIdSet]);
	}
	
});
