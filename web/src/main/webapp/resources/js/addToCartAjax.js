const addToCartAjax = function (id) {
    let cartItem = {
        phoneId: id,
        quantity: $('#phone' + id + 'Quantity').val()
    }
    const url = "/phoneshop-web/ajaxCart";

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
        success:
            function (response) {
                $('#quantity' + id + 'Message').html(response.msg).show();
                $('#quantity' + id + 'Error').hide();
                $('#totalQuantity').html(response.totalQuantity).show();
                $('#totalPrice').html(response.totalPrice).show();
            },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                $('#quantity' + id + 'Message').hide();
                $('#quantity' + id + 'Error').html(JSON.parse(xhr.responseText)["msg"]).show();
            }
        },
    })
}
