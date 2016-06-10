$(document).ready(function() {
	$("input[name='application_no']").focus() ;
	$(".payment").on('click',function(e){
		var appno = $("input[name='application_no']").val();
		var amount = $("input[name='amount']").val();
		//alert(amount);
		if(appno != ""){
			if(amount >= 10){
				var go = confirm("Continue to payment ?");
				if(go){
					$('PaymentDetailsForm').submit();
				}
				return true ;
			}
			else{
				alert("Enter valid amount for payment !!!");
				$("input[name='amount']").focus() ;
				return false ;
			}
		}else{
			alert("Application number required !!!");
			$("input[name='application_no']").focus() ;
			return false ;
		}
	});
});