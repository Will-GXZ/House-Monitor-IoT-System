// ('.show-card').click(function(e) {
// ('.card').addClass('show').css('display', 'block');
// ('.show-card').addClass('hide');
// });

// ('.card .close').click(function(e) {
// ('.card').addClass('hide');
//   setTimeout(function() {
// ('.card').css('display', 'none').removeClass('show').removeClass('hide');
//   }, 1000);
// ('.show-card').removeClass('hide');
// });

function display(id)
{
	id.innerHTML = "input ipv6 below:";
	document.querySelector(".card").style.display = "block";
	document.querySelector(".addr").style.display = "block";
	
}