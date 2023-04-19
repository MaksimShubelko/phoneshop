const addToCartAjax = function (id) {
    let cartItem = {
        phoneId: id,
        quantity: $('#phone' + id + 'Quantity').val()
    }
    const url = "${pageContext.request.contextPath}/ajaxCart";

    $.ajax({
        headers: {
            "Accept": "application/json; odata=verbose",
            "Content-Type": "application/json; odata=verbose"
        },
        type: "POST",
        url: url,
        data: JSON.stringify(cartItem),
        dataType: "json",
        processData: false,

        success: function (response) {
            if (response.error != null) {
                $('#quantity' + id + 'Error').html(response.error).show()
                $('#quantity' + id + 'Message').hide();
            }
            if (response.message != null) {
                $('#quantity' + id + 'Message').html(response.message).show();
                $('#quantity' + id + 'Error').hide();
                updateCartInfo(id);
            }
        },
    });
}
const updateCartInfo = function (id) {
    let totalQuantity = Number($('#totalQuantity').text()) + Number($('#phone' + id + 'Quantity').val())
    let totalPrice = Number($('#totalPrice').text()) + Number($('#phone' + id + 'Quantity').val()) * Number($('#phone' + id + 'Price').text())
    $('#totalQuantity').html(totalQuantity).show();
    $('#totalPrice').html(totalPrice).show();
}